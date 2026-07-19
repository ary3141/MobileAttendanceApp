//package com.example.mobilesurapp.UIApp.Camera
//
//import android.util.Log
//import androidx.camera.core.CameraSelector
//import androidx.camera.core.Preview
//import androidx.camera.lifecycle.ProcessCameraProvider
//import androidx.camera.view.PreviewView
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Cameraswitch
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Scaffold
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.viewinterop.AndroidView
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.withContext
//import androidx.hilt.navigation.compose.hiltViewModel
//import androidx.annotation.OptIn
//import androidx.camera.core.ExperimentalGetImage
//import androidx.compose.foundation.layout.Row
//import androidx.compose.material.icons.filled.Person
//
//@OptIn(ExperimentalGetImage::class)
//@Composable
//fun CameraScreen(
//    viewModel: CameraViewModel = hiltViewModel(),
//    onNavigateToProfile: () -> Unit
//) {
//    val context = LocalContext.current
//    val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current
//
//    val lensFacing by viewModel.lensFacing.collectAsState()
//    val previewView = remember {
//        PreviewView(context).also {
//            it.scaleType = PreviewView.ScaleType.FIT_CENTER
//        }
//    }
//
//    // Camera binding
//    LaunchedEffect(lensFacing) {
//        val cameraProvider = withContext(Dispatchers.Main) {
//            ProcessCameraProvider.getInstance(context).get()
//        }
//
//        val preview = Preview.Builder().build().apply {
//            setSurfaceProvider(previewView.surfaceProvider)
//        }
//
//        val selector = CameraSelector.Builder()
//            .requireLensFacing(lensFacing)
//            .build()
//
//        try {
//            cameraProvider.unbindAll()
//            cameraProvider.bindToLifecycle(
//                lifecycleOwner,
//                selector,
//                preview
//            )
//        } catch (e: Exception) {
//            Log.e("CameraScreen", "Camera binding failed", e)
//        }
//    }
//
//    Scaffold(
//        modifier = Modifier.background(color = Color.LightGray)
//    ) { paddingValues ->
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(paddingValues)
//        ) {
//            Box(
//                modifier = Modifier
//                    .weight(1f)
//                    .fillMaxWidth()
//            ) {
//                AndroidView(
//                    factory = { previewView },
//                    modifier = Modifier.fillMaxSize()
//                )
//
//                IconButton(
//                    onClick = { viewModel.switchCamera() },
//                    modifier = Modifier
//                        .align(Alignment.BottomStart)
//                        .padding(16.dp)
//                        .size(48.dp)
//                        .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(12.dp))
//                ) {
//                    Icon(
//                        imageVector = Icons.Default.Cameraswitch,
//                        contentDescription = "Switch Camera"
//                    )
//                }
//
//                IconButton(
//                    onClick = { onNavigateToProfile() },
//                    modifier = Modifier
//                        .align(Alignment.TopEnd)
//                        .padding(16.dp)
//                        .size(48.dp)
//                        .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(12.dp))
//                ) {
//                    Icon(
//                        imageVector = Icons.Default.Person,
//                        contentDescription = "Profile"
//                    )
//                }
//            }
//        }
//    }
//}
//


package com.example.mobilesurapp.UIApp.Camera

import android.util.Log
import android.util.Size
import android.widget.Toast
import androidx.annotation.OptIn
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cameraswitch
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mobilesurapp.UIApp.components.FaceOverlay
import com.example.mobilesurapp.domain.utils.MediaPipeUtils.toBitmapWithoutConverter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.example.mobilesurapp.UIApp.components.BottomNavBar

@OptIn(ExperimentalGetImage::class)
@Composable
fun CameraScreen(
    viewModel: CameraViewModel = hiltViewModel(),
    onNavigateToProfile: () -> Unit,
    onNavigateToAddFace: () -> Unit
) {

    val context = LocalContext.current
    val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current

    val lensFacing by viewModel.lensFacing.collectAsState()
    val detectionResult by viewModel.detectionResult.collectAsState()
    val isFaceDetected by viewModel.isFaceDetected.collectAsState()
    val verificationResult by viewModel.verificationResult.collectAsState()

    val previewView = remember {
        PreviewView(context).also {
            it.scaleType = PreviewView.ScaleType.FIT_CENTER
        }
    }

    var imageWidth by remember { mutableStateOf(1) }
    var imageHeight by remember { mutableStateOf(1) }

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    LaunchedEffect(isFaceDetected) {
        if (isFaceDetected) {
            snackbarHostState.showSnackbar(
                message = "Face Detected",
                duration = SnackbarDuration.Short
            )
        }
    }

    LaunchedEffect(verificationResult) {

        verificationResult?.let { result ->

            if (result.isMatch) {

                Toast.makeText(
                    context,
                    "Face recognized : ${result.matchedUser?.name}",
                    Toast.LENGTH_SHORT
                ).show()

                // TODO
                // Here later we'll send INSERT_ATTENDANCE

            } else if (result.distance != -1f) {

                Toast.makeText(
                    context,
                    "Face not recognized",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }

    }

    LaunchedEffect(lensFacing) {

        val cameraProvider = withContext(Dispatchers.Main) {
            ProcessCameraProvider.getInstance(context).get()
        }

        val preview = Preview.Builder()
            .build()
            .apply {
                setSurfaceProvider(previewView.surfaceProvider)
            }

        val selector = CameraSelector.Builder()
            .requireLensFacing(lensFacing)
            .build()

        val imageAnalyzer =
            ImageAnalysis.Builder()
                .setTargetResolution(Size(480, 640))
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

                            imageWidth = imageProxy.width
                            imageHeight = imageProxy.height

                            val rotation =
                                imageProxy.imageInfo.rotationDegrees

                            bitmap?.let {

                                viewModel.processFrame(
                                    it,
                                    rotation
                                )

                            }

                        } catch (e: Exception) {

                            Log.e(
                                "CameraScreen",
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
                "CameraScreen",
                "Binding failed",
                e
            )

        }

    }
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },

        bottomBar = {

            BottomNavBar(
                onAddClick = {
                    onNavigateToAddFace()
                }
            )

        },
        modifier = Modifier.background(Color.LightGray)
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {

                AndroidView(
                    factory = { previewView },
                    modifier = Modifier.fillMaxSize()
                )

                detectionResult?.let { result ->

                    FaceOverlay(
                        modifier = Modifier.fillMaxSize(),
                        detectionResult = result,
                        imageWidth = imageWidth,
                        imageHeight = imageHeight,
                        isFrontCamera = lensFacing == CameraSelector.LENS_FACING_FRONT
                    )

                }

                verificationResult?.let { result ->

                    Text(
                        text =
                            if (result.isMatch)
                                "Face Recognized"
                            else
                                "Face Not Recognized",

                        color =
                            if (result.isMatch)
                                Color.Green
                            else
                                Color.Red,

                        style = MaterialTheme.typography.titleLarge,

                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .padding(top = 20.dp)
                            .background(
                                Color.Black.copy(alpha = 0.55f),
                                RoundedCornerShape(10.dp)
                            )
                            .padding(
                                horizontal = 16.dp,
                                vertical = 8.dp
                            )

                    )

                }

                IconButton(
                    onClick = {
                        onNavigateToProfile()
                    },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(16.dp)
                        .size(52.dp)
                        .background(
                            MaterialTheme.colorScheme.surface,
                            RoundedCornerShape(12.dp)
                        )
                ) {

                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Profile"
                    )

                }

                IconButton(
                    onClick = {

                        viewModel.switchCamera()

                    },
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp)
                        .size(52.dp)
                        .background(
                            MaterialTheme.colorScheme.surface,
                            RoundedCornerShape(12.dp)
                        )
                ) {

                    Icon(
                        imageVector = Icons.Default.Cameraswitch,
                        contentDescription = "Switch Camera"
                    )

                }
            }
        }
    }
}