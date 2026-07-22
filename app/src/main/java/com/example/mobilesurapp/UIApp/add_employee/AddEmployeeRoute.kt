package com.example.mobilesurapp.UIApp.add_employee

import android.util.Log
import android.util.Size
import androidx.annotation.OptIn
import androidx.activity.compose.BackHandler
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.mobilesurapp.UIApp.add_employee.components.AddEmployeeTopBar
import com.example.mobilesurapp.UIApp.add_employee.model.AddEmployeeStep
import com.example.mobilesurapp.UIApp.attendance.model.CameraState
import com.example.mobilesurapp.UIApp.add_employee.screens.EmployeeInfoStep
import com.example.mobilesurapp.UIApp.add_employee.screens.CaptureFaceStep
import com.example.mobilesurapp.UIApp.add_employee.screens.ReviewStep
import com.example.mobilesurapp.domain.utils.MediaPipeUtils.toBitmapWithoutConverter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@OptIn(ExperimentalGetImage::class)
@Composable
fun AddEmployeeRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AddEmployeeViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    val cameraState by viewModel.cameraState.collectAsState()

    val context = LocalContext.current

    val lifecycleOwner = LocalLifecycleOwner.current

    val previewView = remember {

        PreviewView(context).apply {

            scaleType = PreviewView.ScaleType.FILL_START

        }

    }

    val snackbarHostState = remember {

        SnackbarHostState()

    }

    LaunchedEffect(cameraState.lensFacing) {

        val cameraProvider = withContext(Dispatchers.Main) {

            ProcessCameraProvider
                .getInstance(context)
                .get()

        }

        val preview =
            Preview.Builder()
                .build()
                .apply {

                    setSurfaceProvider(
                        previewView.surfaceProvider
                    )

                }

        val selector =
            CameraSelector.Builder()
                .requireLensFacing(
                    cameraState.lensFacing
                )
                .build()

        val imageAnalyzer =
            ImageAnalysis.Builder()
                .setBackpressureStrategy(
                    ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST
                )
                .build()
                .also { analysis ->

                    analysis.setAnalyzer(
                        ContextCompat.getMainExecutor(context)
                    ) { imageProxy ->

                        try {

                            viewModel.updateImageSize(
                                imageProxy.width,
                                imageProxy.height
                            )

                            val bitmap =
                                imageProxy.toBitmapWithoutConverter()

                            val rotation =
                                imageProxy.imageInfo.rotationDegrees

                            bitmap?.let {

                                viewModel.processFrame(
                                    bitmap = it,
                                    rotationDegrees = rotation
                                )

                            }

                        } catch (e: Exception) {

                            Log.e(
                                "AddEmployeeRoute",
                                "Analyzer Error",
                                e
                            )

                        } finally {

                            imageProxy.close()

                        }

                    }

                }

        try {

            cameraProvider.unbindAll()

            cameraProvider.bindToLifecycle(
                lifecycleOwner,
                selector,
                preview,
                imageAnalyzer
            )

        } catch (e: Exception) {

            Log.e(
                "AddEmployeeRoute",
                "Camera binding failed",
                e
            )

        }

    }

    LaunchedEffect(cameraState.isFaceDetected) {

        if (cameraState.isFaceDetected) {

            snackbarHostState.showSnackbar(

                message = "Face Detected!",

                duration = SnackbarDuration.Short

            )

        }

    }

    DisposableEffect(Unit) {

        onDispose {

            viewModel.resetState()

        }

    }

    BackHandler {

        when (uiState.currentStep) {

            AddEmployeeStep.INFORMATION ->

                onBackClick()

            AddEmployeeStep.CAPTURE_FACE ->

                viewModel.previousStep()

            AddEmployeeStep.REVIEW ->

                viewModel.previousStep()

        }

    }
    Scaffold(

        modifier = modifier,

        snackbarHost = {

            SnackbarHost(
                hostState = snackbarHostState
            )

        },

        topBar = {

            AddEmployeeTopBar(

                onBackClick = {

                    when (uiState.currentStep) {

                        AddEmployeeStep.INFORMATION ->

                            onBackClick()

                        AddEmployeeStep.CAPTURE_FACE ->

                            viewModel.previousStep()

                        AddEmployeeStep.REVIEW ->

                            viewModel.previousStep()

                    }

                }

            )

        }

    ) { paddingValues ->

        Box(

            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)

        ) {

            when (uiState.currentStep) {

                AddEmployeeStep.INFORMATION -> {

                    EmployeeInfoStep(

                        fullName = uiState.fullName,

                        employeeId = uiState.employeeId,

                        email = uiState.email,

                        phoneNumber = uiState.phoneNumber,

                        onFullNameChange = viewModel::onFullNameChange,

                        onEmployeeIdChange = viewModel::onEmployeeIdChange,

                        onEmailChange = viewModel::onEmailChange,

                        onPhoneNumberChange = viewModel::onPhoneNumberChange,

                        fullNameError = uiState.fullNameError,

                        employeeIdError = uiState.employeeIdError,

                        emailError = uiState.emailError,

                        phoneNumberError = uiState.phoneNumberError,

                        onNextClick = {

                            viewModel.nextStep()

                        }

                    )

                }

                AddEmployeeStep.CAPTURE_FACE -> {

                    CaptureFaceStep(

                        previewView = previewView,

                        detectionResult =
                            cameraState.detectionResult,

                        imageSize =
                            cameraState.imageSize,

                        lensFacing =
                            cameraState.lensFacing,

                        recordingState =
                            uiState.recordingState,

                        isFaceDetected =
                            cameraState.isFaceDetected,

                        onCaptureClick = {
                            viewModel.startRecording()
                        },

                        onRotateClick = {
                            viewModel.switchCamera()
                        }
                    )

                }

                AddEmployeeStep.REVIEW -> {

                    ReviewStep(

                        fullName =
                            uiState.fullName,

                        employeeId =
                            uiState.employeeId,

                        email =
                            uiState.email,

                        phoneNumber =
                            uiState.phoneNumber,

                        faceBitmap =
                            uiState.capturedFace,

                        onEditClick = {

                            viewModel.previousStep()

                        },

                        onRetakeClick = {

                            viewModel.retakeFace()

                        },

                        onRegisterClick = {

                            viewModel.registerEmployee()

                        }

                    )

                }

            }

        }

    }

}