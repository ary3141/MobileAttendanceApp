package com.example.mobilesurapp.UIApp.add_employee.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mobilesurapp.UIApp.add_employee.model.RecordingState

@Composable
fun FaceGuide(
    isFaceDetected: Boolean,
    recordingState: RecordingState,
    modifier: Modifier = Modifier
) {

    val title: String
    val description: String
    val icon = if (isFaceDetected) {
        Icons.Default.CheckCircle
    } else {
        Icons.Default.Face
    }

    when (recordingState) {

        RecordingState.IDLE -> {

            if (isFaceDetected) {
                title = "Face Detected"
                description = "Great! Press the button below to start recording."
            } else {
                title = "Position Your Face"
                description = "Align your face inside the camera frame."
            }

        }

        RecordingState.RECORDING -> {

            title = "Recording"
            description =
                "Slowly turn your head left and right while keeping your face visible."

        }

        RecordingState.PROCESSING -> {

            title = "Processing"
            description =
                "Please wait while we process your facial data."

        }

        RecordingState.DONE -> {

            title = "Completed"
            description =
                "Face registration completed successfully."

        }

    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = if (isFaceDetected)
                Color(0xFF2E7D32)
            else
                MaterialTheme.colorScheme.primary
        )

        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(top = 8.dp)
        )

        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 4.dp)
        )

    }

}

@Preview(showBackground = true)
@Composable
private fun FaceGuidePreview() {

    MaterialTheme {

        FaceGuide(
            isFaceDetected = true,
            recordingState = RecordingState.IDLE
        )

    }

}