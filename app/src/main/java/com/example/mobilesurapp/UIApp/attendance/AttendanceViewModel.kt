package com.example.mobilesurapp.UIApp.attendance

import android.app.Application
import android.graphics.Bitmap
import android.graphics.Matrix
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobilesurapp.UIApp.attendance.model.AttendanceStatus
import com.example.mobilesurapp.UIApp.attendance.model.AttendanceUiState
import com.example.mobilesurapp.UIApp.attendance.model.CameraState
import com.example.mobilesurapp.api.ApiResult
import com.example.mobilesurapp.domain.usecase.SyncOfflineFacesUseCase
import com.example.mobilesurapp.domain.usecase.VerifyFaceUseCase
import com.example.mobilesurapp.domain.utils.ImageCropper
import com.example.mobilesurapp.face.FaceEmbedder
import com.example.mobilesurapp.modelload.MediaPipeFaceDetector
import com.google.mediapipe.tasks.vision.facedetector.FaceDetectorResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.Job

@HiltViewModel
class AttendanceViewModel @Inject constructor(
    application: Application,
    private val faceEmbedder: FaceEmbedder,
    private val verifyFaceUseCase: VerifyFaceUseCase,
    private val syncOfflineFacesUseCase: SyncOfflineFacesUseCase
) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(
        AttendanceUiState()
    )

    val uiState: StateFlow<AttendanceUiState> =
        _uiState.asStateFlow()

    private var faceDetector: MediaPipeFaceDetector? = null

    private var lastBitmap: Bitmap? = null
    private var lastRotation = 0

    private val cropExpansionFactor = 0.9f

    private var isCollecting = false

    private var latestDetection: FaceDetectorResult? = null

    private var collectionJob: Job? = null

    init {
        initializeDetector()
        startPeriodicSync()
    }

    private fun initializeDetector() {

        faceDetector = MediaPipeFaceDetector(
            context = getApplication(),

            onResult = { result ->
                handleDetection(result)
            },

            onError = {
                Log.e("AttendanceVM", it.message ?: "")
            }
        )

    }

    private var lensFacing =
        CameraSelector.LENS_FACING_FRONT

    fun switchCamera() {

        lensFacing =
            if (lensFacing == CameraSelector.LENS_FACING_FRONT)
                CameraSelector.LENS_FACING_BACK
            else
                CameraSelector.LENS_FACING_FRONT

        _uiState.update {

            it.copy(
                cameraState =
                    if (lensFacing == CameraSelector.LENS_FACING_FRONT)
                        CameraState.FRONT
                    else
                        CameraState.BACK
            )

        }

    }

    fun processFrame(
        bitmap: Bitmap,
        rotationDegrees: Int
    ) {

        lastBitmap?.recycle()

        lastBitmap = bitmap.copy(
            bitmap.config ?: Bitmap.Config.ARGB_8888,
            true
        )

        lastRotation = rotationDegrees

        faceDetector?.detect(bitmap)

    }

    private fun handleDetection(
        result: FaceDetectorResult
    ) {

        if (result.detections().isEmpty()) {

            if (!isCollecting) {

                _uiState.update {

                    it.copy(
                        status = AttendanceStatus.Idle,
                        isFaceDetected = false
                    )

                }

            }

            return

        }

        latestDetection = result

        if (isCollecting) return

        isCollecting = true

        _uiState.update {

            it.copy(
                status = AttendanceStatus.Collecting,
                isFaceDetected = true
            )

        }

        collectionJob?.cancel()

        collectionJob = viewModelScope.launch {

            for (second in COUNTDOWN_SECONDS downTo 1) {
                _uiState.update {
                    it.copy(countdown = second)
                }
                delay(1000)
            }

            verifyCollectedFace()

        }

    }

    private fun verifyCollectedFace() {

        val detection = latestDetection

        if (detection == null) {

            resetScanner()

            return

        }

        verifyFace(detection)

    }

    private fun verifyFace(
        detectionResult: FaceDetectorResult
    ) {

        viewModelScope.launch(Dispatchers.Default) {

            _uiState.update {
                it.copy(
                    status = AttendanceStatus.Verifying,
                    isVerifying = true
                )
            }

            try {

                val bitmap = lastBitmap ?: run {

                    showFailure()

                    return@launch

                }

                val boundingBox =
                    detectionResult
                        .detections()
                        .first()
                        .boundingBox()

                val expandedBox =
                    ImageCropper.expandBoundingBox(
                        boundingBox = boundingBox,
                        imageWidth = bitmap.width,
                        imageHeight = bitmap.height,
                        expansionFactor = cropExpansionFactor
                    )

                var cropped =
                    ImageCropper.cropBitmap(
                        bitmap,
                        expandedBox
                    )

                if (lastRotation != 0) {

                    val matrix = Matrix()

                    matrix.postRotate(
                        lastRotation.toFloat()
                    )

                    val rotated =
                        Bitmap.createBitmap(
                            cropped,
                            0,
                            0,
                            cropped.width,
                            cropped.height,
                            matrix,
                            true
                        )

                    cropped.recycle()

                    cropped = rotated
                }

                if (lensFacing == CameraSelector.LENS_FACING_FRONT) {

                    val flip = Matrix()

                    flip.postScale(
                        -1f,
                        1f,
                        cropped.width / 2f,
                        cropped.height / 2f
                    )

                    val mirrored =
                        Bitmap.createBitmap(
                            cropped,
                            0,
                            0,
                            cropped.width,
                            cropped.height,
                            flip,
                            true
                        )

                    cropped.recycle()

                    cropped = mirrored
                }

                val embedding =
                    faceEmbedder.getEmbeddings(cropped)

                cropped.recycle()

                if (embedding == null) {

                    showFailure()

                    return@launch
                }

                when (
                    val result =
                        verifyFaceUseCase(embedding)
                ) {

                    is ApiResult.Success -> {

                        val verification =
                            result.data

                        if (
                            verification.isMatch &&
                            verification.matchedEmployee != null
                        ) {

                            showSuccess(
                                verification.matchedEmployee.name,
                                verification.distance
                            )

                        } else {

                            showFailure()

                        }

                    }

                    is ApiResult.Error -> {

                        Log.e(
                            "AttendanceVM",
                            result.message,
                            result.exception
                        )

                        showFailure()

                    }

                    ApiResult.Loading -> Unit

                }

                embedding.fill(0f)

            } catch (e: Exception) {

                Log.e(
                    "AttendanceVM",
                    e.message ?: "",
                    e
                )

                showFailure()

            } finally {

                latestDetection = null

            }

        }

    }
    private suspend fun showSuccess(
        name: String,
        similarity: Float?
    ) {

        _uiState.update {

            it.copy(
                status = AttendanceStatus.Success(name),
                employeeName = name,
                similarity = similarity,
                isVerifying = false
            )

        }

        delay(SUCCESS_DURATION)

        resetScanner()

    }
    private suspend fun showFailure(
        message: String = "Face not recognized"
    ) {

        _uiState.update {

            it.copy(
                status = AttendanceStatus.Failed(message),
                employeeName = null,
                similarity = null,
                isVerifying = false
            )

        }

        delay(FAILURE_DURATION)

        resetScanner()

    }

    private fun startPeriodicSync() {

        viewModelScope.launch {

            while (true) {

                delay(30000)

                syncOfflineFacesUseCase()

            }

        }

    }
    private fun resetScanner() {

        collectionJob?.cancel()

        isCollecting = false

        latestDetection = null

        _uiState.update {
            AttendanceUiState(
                cameraState = it.cameraState,
                countdown = 0
            )
        }

    }

    companion object {
        private const val COUNTDOWN_SECONDS = 3
        private const val SUCCESS_DURATION = 2500L
        private const val FAILURE_DURATION = 2000L
    }

    override fun onCleared() {

        super.onCleared()

        faceDetector?.close()

        lastBitmap?.recycle()

        lastBitmap = null

    }


}