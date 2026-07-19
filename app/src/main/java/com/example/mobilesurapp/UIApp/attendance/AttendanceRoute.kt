package com.example.mobilesurapp.UIApp.attendance

import android.util.Log
import android.util.Size
import androidx.annotation.OptIn
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.mobilesurapp.UIApp.attendance.model.CameraState
import com.example.mobilesurapp.UIApp.attendance.AttendanceScreen
import com.example.mobilesurapp.domain.utils.MediaPipeUtils.toBitmapWithoutConverter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@OptIn(ExperimentalGetImage::class)
@Composable
fun AttendanceRoute(
    onExitClick: () -> Unit
) {

    val viewModel: AttendanceViewModel = hiltViewModel()

    val uiState by viewModel.uiState.collectAsState()

    val context = LocalContext.current

    val lifecycleOwner = LocalLifecycleOwner.current

    val previewView = remember {

        PreviewView(context).apply {
            scaleType = PreviewView.ScaleType.FILL_START
        }

    }

    LaunchedEffect(uiState.cameraState) {

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
                    when (uiState.cameraState) {
                        CameraState.FRONT -> CameraSelector.LENS_FACING_FRONT
                        CameraState.BACK -> CameraSelector.LENS_FACING_BACK
                    }
                )
                .build()

        val imageAnalyzer =
            ImageAnalysis.Builder()
                .setTargetResolution(
                    Size(480, 640)
                )
                .setBackpressureStrategy(
                    ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST
                )
                .build()
                .also { analysis ->

                    analysis.setAnalyzer(
                        ContextCompat.getMainExecutor(context)
                    ) { imageProxy ->

                        try {

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
                                "AttendanceRoute",
                                "Analyzer error",
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
                "AttendanceRoute",
                "Camera binding failed",
                e
            )

        }

    }

    AttendanceScreen(
        uiState = uiState,
        previewView = previewView,
        onExitClick = onExitClick,
        onSwitchCameraClick = {
            viewModel.switchCamera()
        }
    )

}