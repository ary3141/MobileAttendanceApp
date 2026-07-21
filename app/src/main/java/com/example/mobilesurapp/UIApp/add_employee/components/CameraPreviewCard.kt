package com.example.mobilesurapp.UIApp.add_employee.components

import android.util.Size
import androidx.camera.core.CameraSelector
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cached
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.mobilesurapp.UIApp.components.FaceOverlay
import com.example.mobilesurapp.UIApp.add_employee.model.RecordingState
import com.google.mediapipe.tasks.vision.facedetector.FaceDetectorResult

@Composable
fun CameraPreviewCard(
    previewView: PreviewView,
    detectionResult: FaceDetectorResult?,
    imageSize: Size,
    lensFacing: Int,
    recordingState: RecordingState,

    onCaptureClick: () -> Unit,
    onRotateClick: () -> Unit,

    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(3f / 4f)
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {

        AndroidView(
            factory = { previewView },
            modifier = Modifier.fillMaxSize()
        )

        detectionResult?.let {

            FaceOverlay(
                modifier = Modifier.fillMaxSize(),
                detectionResult = it,
                imageWidth = imageSize.width,
                imageHeight = imageSize.height,
                isFrontCamera = lensFacing == CameraSelector.LENS_FACING_FRONT
            )

        }

        if (recordingState == RecordingState.PROCESSING) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.45f)),
                contentAlignment = Alignment.Center
            ) {

                CircularProgressIndicator()

            }

        }

        if (recordingState != RecordingState.PROCESSING) {

            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .background(Color.Black.copy(alpha = 0.45f))
                    .navigationBarsPadding()
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                // Capture
                Box(
                    modifier = Modifier
                        .size(78.dp)
                        .clip(CircleShape)
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {

                    Box(
                        modifier = Modifier
                            .size(62.dp)
                            .clip(CircleShape)
                            .border(
                                width = 3.dp,
                                color = Color.Black,
                                shape = CircleShape
                            )
                            .background(Color.White)
                    )

                    IconButton(
                        modifier = Modifier.fillMaxSize(),
                        onClick = onCaptureClick
                    ) {}

                }

                // Rotate
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.clickable(onClick = onRotateClick)
                ) {

                    Box(
                        modifier = Modifier
                            .size(52.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Cached,
                            contentDescription = "Rotate",
                            tint = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Rotate",
                        color = Color.White
                    )
                }

            }

        }

    }

}