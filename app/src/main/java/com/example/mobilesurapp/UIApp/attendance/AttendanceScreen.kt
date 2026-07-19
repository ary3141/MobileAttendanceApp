package com.example.mobilesurapp.UIApp.attendance

import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mobilesurapp.UIApp.attendance.components.AttendanceActionBar
import com.example.mobilesurapp.UIApp.attendance.components.AttendanceStatusCard
import com.example.mobilesurapp.UIApp.attendance.components.CameraPreviewCard
import com.example.mobilesurapp.UIApp.attendance.model.AttendanceStatus
import com.example.mobilesurapp.ui.theme.MobileSurAppTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import com.example.mobilesurapp.UIApp.attendance.model.AttendanceUiState

@Composable
fun AttendanceScreen(
    uiState: AttendanceUiState,
    previewView: PreviewView,
    onExitClick: () -> Unit,
    onSwitchCameraClick: () -> Unit
) {

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Black
    ) {

        Box(
            modifier = Modifier.fillMaxSize()
        ) {

            CameraPreviewCard(
                modifier = Modifier.fillMaxSize(),
                previewView = previewView,
                uiState = uiState,
                onExitClick = onExitClick
            )

            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .height(280.dp)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = .75f),
                                Color.Black
                            )
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(horizontal = 24.dp, vertical = 32.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                AttendanceStatusCard(
                    status = uiState.status
                )

                AttendanceActionBar(
                    onSwitchCameraClick = onSwitchCameraClick
                )

            }

        }

    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun AttendanceScreenPreview() {

    val context = LocalContext.current

    MobileSurAppTheme {

        AttendanceScreen(
            uiState = AttendanceUiState(),
            previewView = PreviewView(context),
            onExitClick = {},
            onSwitchCameraClick = {}
        )

    }

}