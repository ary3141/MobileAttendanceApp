package com.example.mobilesurapp.UIApp.attendance.components

import androidx.camera.view.PreviewView
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CameraAlt
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.mobilesurapp.ui.theme.MobileSurAppTheme
import com.example.mobilesurapp.UIApp.attendance.model.AttendanceStatus
import com.example.mobilesurapp.UIApp.attendance.model.AttendanceUiState
import androidx.compose.runtime.getValue

@Composable
fun CameraPreviewCard(
    previewView: PreviewView,
    uiState: AttendanceUiState,
    onExitClick: () -> Unit,
    modifier: Modifier = Modifier,
    showFaceGuide: Boolean = true
){
    val targetColor = when {

        uiState.isVerifying ->
            Color(0xFF3B82F6)

        uiState.status is AttendanceStatus.Success ->
            Color(0xFF22C55E)

        uiState.status is AttendanceStatus.Failed ->
            Color(0xFFE53935)

        uiState.isFaceDetected ->
            Color(0xFF18C964)

        else ->
            Color.White.copy(alpha = .85f)
    }

    val guideColor by animateColorAsState(
        targetValue = targetColor,
        label = "FaceGuideColor"
    )

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(
            bottomStart = 36.dp,
            bottomEnd = 36.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.Black
        ),
        elevation = CardDefaults.cardElevation(0.dp)
    ){

        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            AttendanceHeader(
                modifier = Modifier.padding(top = 4.dp),
                onExitClick = onExitClick
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(
                        RoundedCornerShape(
                            bottomStart = 28.dp,
                            bottomEnd = 28.dp
                        )
                    )
            ) {

                if (LocalInspectionMode.current) {

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0xFF202124)),
                        contentAlignment = Alignment.Center
                    ) {

                        Icon(
                            imageVector = Icons.Rounded.CameraAlt,
                            contentDescription = null,
                            tint = Color.White.copy(alpha = .7f)
                        )

                    }

                } else {

                    AndroidView(
                        modifier = Modifier.fillMaxSize(),
                        factory = { previewView }
                    )

                }

                if (showFaceGuide) {
                    FaceGuide(
                        status = uiState.status,
                        modifier = Modifier.fillMaxSize()
                    )
                }

            }

        }

    }

}

@Preview(showBackground = true, backgroundColor = 0xFF0F1013)
@Composable
private fun CameraPreviewCardPreview() {

    val context = LocalContext.current

    MobileSurAppTheme {
        CameraPreviewCard(
            previewView = PreviewView(context),
            uiState = AttendanceUiState(),
            onExitClick = {}
        )

    }
}
