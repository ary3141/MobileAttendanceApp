package com.example.mobilesurapp.UIApp.add_employee

import android.app.Application
import android.graphics.Bitmap
import android.graphics.Matrix
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobilesurapp.UIApp.add_employee.model.AddEmployeeStep
import com.example.mobilesurapp.UIApp.add_employee.model.AddEmployeeUiState
import com.example.mobilesurapp.UIApp.add_employee.model.CameraState
import com.example.mobilesurapp.UIApp.add_employee.model.RecordingState
import com.example.mobilesurapp.api.ApiResult
import com.example.mobilesurapp.domain.usecase.RegisterUserWithFaceUseCase
import com.example.mobilesurapp.domain.utils.ImageCropper
import com.example.mobilesurapp.face.FaceEmbedder
import com.example.mobilesurapp.modelload.MediaPipeFaceDetector
import com.google.mediapipe.tasks.vision.facedetector.FaceDetectorResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEmployeeViewModel @Inject constructor(

    application: Application,

    private val faceEmbedder: FaceEmbedder,

    private val registerUserWithFaceUseCase: RegisterUserWithFaceUseCase

) : AndroidViewModel(application) {

    companion object {

        private const val TAG = "AddEmployeeVM"

    }

    private val _uiState =
        MutableStateFlow(AddEmployeeUiState())

    val uiState: StateFlow<AddEmployeeUiState> =
        _uiState.asStateFlow()

    private val _cameraState =
        MutableStateFlow(CameraState())

    val cameraState: StateFlow<CameraState> =
        _cameraState.asStateFlow()

    private var detector: MediaPipeFaceDetector? = null

    private var latestBitmap: Bitmap? = null

    private var latestRotation = 0

    private var averagedEmbedding: FloatArray? = null

    private var collectJob: Job? = null

    init {

        initializeDetector()

    }

    private fun initializeDetector() {

        detector = MediaPipeFaceDetector(

            context = getApplication(),

            onResult = {

                handleDetection(it)

            },

            onError = {

                Log.e(
                    TAG,
                    it.message ?: ""
                )

            }

        )

    }

    fun switchCamera() {

        val current =
            _cameraState.value.lensFacing

        val newFacing =

            if (current == CameraSelector.LENS_FACING_FRONT)

                CameraSelector.LENS_FACING_BACK

            else

                CameraSelector.LENS_FACING_FRONT

        _cameraState.update {

            it.copy(
                lensFacing = newFacing
            )

        }

    }

    fun updateImageSize(width: Int, height: Int) {

        _cameraState.update {

            it.copy(
                imageSize = android.util.Size(
                    width,
                    height
                )
            )

        }

    }

    fun processFrame(

        bitmap: Bitmap,

        rotationDegrees: Int

    ) {

        latestBitmap?.recycle()

        latestBitmap =
            bitmap.copy(
                bitmap.config
                    ?: Bitmap.Config.ARGB_8888,
                true
            )

        latestRotation =
            rotationDegrees

        detector?.detect(bitmap)

    }

    fun onFullNameChange(value: String) {

        _uiState.update {

            it.copy(

                fullName = value,

                fullNameError = null

            )

        }

    }

    fun onEmployeeIdChange(value: String) {

        _uiState.update {

            it.copy(

                employeeId = value,

                employeeIdError = null

            )

        }

    }

    fun onEmailChange(value: String) {

        _uiState.update {

            it.copy(

                email = value,

                emailError = null

            )

        }

    }

    fun onPhoneNumberChange(value: String) {

        _uiState.update {

            it.copy(

                phoneNumber = value,

                phoneNumberError = null

            )

        }

    }

    fun nextStep() {

        when (_uiState.value.currentStep) {

            AddEmployeeStep.INFORMATION -> {

                if (validateForm()) {

                    _uiState.update {

                        it.copy(

                            currentStep =
                                AddEmployeeStep.CAPTURE_FACE

                        )

                    }

                }

            }

            AddEmployeeStep.CAPTURE_FACE -> {

                if (_uiState.value.capturedFace != null) {

                    _uiState.update {

                        it.copy(

                            currentStep =
                                AddEmployeeStep.REVIEW

                        )

                    }

                }

            }

            AddEmployeeStep.REVIEW -> Unit

        }

    }

    fun previousStep() {

        when (_uiState.value.currentStep) {

            AddEmployeeStep.INFORMATION -> Unit

            AddEmployeeStep.CAPTURE_FACE -> {

                _uiState.update {

                    it.copy(

                        currentStep =
                            AddEmployeeStep.INFORMATION

                    )

                }

            }

            AddEmployeeStep.REVIEW -> {

                _uiState.update {

                    it.copy(

                        currentStep =
                            AddEmployeeStep.CAPTURE_FACE

                    )

                }

            }

        }

    }

    private fun validateForm(): Boolean {

        var valid = true

        _uiState.update { state ->

            state.copy(

                fullNameError =

                    if (state.fullName.isBlank()) {

                        valid = false

                        "Required"

                    } else null,

                employeeIdError =

                    if (state.employeeId.isBlank()) {

                        valid = false

                        "Required"

                    } else null,

                emailError =

                    if (state.email.isBlank()) {

                        valid = false

                        "Required"

                    } else null,

                phoneNumberError =

                    if (state.phoneNumber.isBlank()) {

                        valid = false

                        "Required"

                    } else null

            )

        }

        return valid

    }
    private var latestDetection: FaceDetectorResult? = null

    private fun handleDetection(
        result: FaceDetectorResult
    ) {

        _cameraState.update {

            it.copy(

                detectionResult = result,

                isFaceDetected = result.detections().isNotEmpty()

            )

        }

        if (result.detections().isEmpty()) {

            latestDetection = null

            collectJob?.cancel()

            _uiState.update {

                it.copy(

                    recordingState = RecordingState.IDLE,

                    recordingProgress = 0f,

                    countdown = 5,

                    isFaceDetected = false

                )

            }

            return

        }

        latestDetection = result

        _uiState.update {

            it.copy(

                isFaceDetected = true

            )

        }

    }

    fun startRecording() {

        if (!_cameraState.value.isFaceDetected) {
            return
        }

        if (collectJob?.isActive == true) {
            return
        }

        collectJob = viewModelScope.launch {

            _uiState.update {

                it.copy(

                    recordingState = RecordingState.RECORDING,

                    recordingProgress = 0f,

                    countdown = 3,

                    error = null

                )

            }

            for (second in 3 downTo 1) {

                _uiState.update {

                    it.copy(
                        countdown = second
                    )

                }

                delay(1000)

            }

            _uiState.update {

                it.copy(

                    recordingState = RecordingState.PROCESSING,

                    countdown = 0

                )

            }

            extractEmbedding()

        }

    }

    private suspend fun extractEmbedding() {

        val bitmap = latestBitmap

        val detection = latestDetection

        if (
            bitmap == null ||
            detection == null
        ) {

            resetCapture()

            return

        }

        try {

            val boundingBox =
                detection
                    .detections()
                    .first()
                    .boundingBox()

            val expandedBox =
                ImageCropper.expandBoundingBox(

                    boundingBox = boundingBox,

                    imageWidth = bitmap.width,

                    imageHeight = bitmap.height,

                    expansionFactor = 0.9f

                )

            var cropped =
                ImageCropper.cropBitmap(

                    bitmap,

                    expandedBox

                )

            if (latestRotation != 0) {

                val matrix = Matrix()

                matrix.postRotate(
                    latestRotation.toFloat()
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

            if (
                _cameraState.value.lensFacing ==
                CameraSelector.LENS_FACING_FRONT
            ) {

                val matrix = Matrix()

                matrix.postScale(

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

                        matrix,

                        true

                    )

                cropped.recycle()

                cropped = mirrored

            }

            val embedding =
                faceEmbedder.getEmbeddings(cropped)

            if (embedding == null) {

                cropped.recycle()

                resetCapture()

                return

            }

            averagedEmbedding = embedding

            _uiState.update {

                it.copy(

                    recordingState = RecordingState.DONE,

                    recordingProgress = 1f,

                    capturedFace = cropped.copy(

                        cropped.config
                            ?: Bitmap.Config.ARGB_8888,

                        true

                    )

                )

            }

            nextStep()

            cropped.recycle()

        } catch (e: Exception) {

            Log.e(
                TAG,
                "extractEmbedding",
                e
            )

            resetCapture()

        }

    }

    private fun resetCapture() {

        collectJob?.cancel()

        latestDetection = null

        averagedEmbedding = null

        _uiState.update {

            it.copy(

                recordingState = RecordingState.IDLE,

                recordingProgress = 0f,

                countdown = 5,

                capturedFace = null,

                isFaceDetected = false

            )

        }

    }
    fun registerEmployee() {

        val embedding = averagedEmbedding

        if (embedding == null) {

            _uiState.update {

                it.copy(
                    error = "Please capture a face first."
                )

            }

            return

        }

        val state = _uiState.value

        viewModelScope.launch {

            _uiState.update {

                it.copy(
                    isLoading = true,
                    error = null
                )

            }

            try {

                when (

                    val result = registerUserWithFaceUseCase(

                        adminId = null,

                        name = state.fullName,

                        email = state.email,

                        phone = state.phoneNumber,

                        department = "",

                        position = "",

                        embeddings = embedding

                    )

                ) {

                    is ApiResult.Success -> {

                        _uiState.update {

                            it.copy(

                                isLoading = false,

                                success = true,

                                error = null

                            )

                        }

                    }

                    is ApiResult.Error -> {

                        _uiState.update {

                            it.copy(

                                isLoading = false,

                                success = false,

                                error = result.message

                            )

                        }

                    }

                    ApiResult.Loading -> Unit

                }

            } catch (e: Exception) {

                Log.e(
                    TAG,
                    "registerEmployee",
                    e
                )

                _uiState.update {

                    it.copy(

                        isLoading = false,

                        success = false,

                        error = e.message ?: "Unknown error"

                    )

                }

            }

        }

    }

    fun retakeFace() {

        averagedEmbedding = null

        latestDetection = null

        collectJob?.cancel()

        _uiState.update {

            it.copy(

                currentStep =
                    AddEmployeeStep.CAPTURE_FACE,

                capturedFace = null,

                recordingState =
                    RecordingState.IDLE,

                recordingProgress = 0f,

                countdown = 5,

                isFaceDetected = false,

                error = null

            )

        }

    }

    fun resetState() {

        collectJob?.cancel()

        latestDetection = null

        averagedEmbedding = null

        latestBitmap?.recycle()

        latestBitmap = null

        _cameraState.value = CameraState()

        _uiState.value = AddEmployeeUiState()

    }

    override fun onCleared() {

        super.onCleared()

        collectJob?.cancel()

        detector?.close()

        detector = null

        latestBitmap?.recycle()

        latestBitmap = null

        averagedEmbedding?.fill(0f)

        averagedEmbedding = null

    }

}