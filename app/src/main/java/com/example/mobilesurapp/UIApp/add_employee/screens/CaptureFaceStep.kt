package com.example.mobilesurapp.UIApp.add_employee.screens

import android.util.Size
import androidx.camera.core.CameraSelector
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mobilesurapp.UIApp.add_employee.components.CameraPreviewCard
import com.example.mobilesurapp.UIApp.add_employee.components.FaceGuide
import com.example.mobilesurapp.UIApp.add_employee.components.StepIndicator
import com.example.mobilesurapp.UIApp.add_employee.components.TipsCard
import com.example.mobilesurapp.UIApp.add_employee.model.RecordingState
import com.example.mobilesurapp.UIApp.components.GradientPrimaryButton
import com.google.mediapipe.tasks.vision.facedetector.FaceDetectorResult

@Composable
fun CaptureFaceStep(
    previewView: PreviewView,

    detectionResult: FaceDetectorResult?,
    imageSize: Size,
    lensFacing: Int,

    isFaceDetected: Boolean,
    recordingState: RecordingState,

    onCaptureClick: () -> Unit,
    onRotateClick: () -> Unit,

    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp)
            .navigationBarsPadding(),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        Spacer(modifier = Modifier.height(20.dp))

        StepIndicator(
            currentStep = 2
        )

        CameraPreviewCard(
            previewView = previewView,
            detectionResult = detectionResult,
            imageSize = imageSize,
            lensFacing = lensFacing,
            recordingState = recordingState,
            onCaptureClick = onCaptureClick,
            onRotateClick = onRotateClick
        )

        FaceGuide(
            isFaceDetected = isFaceDetected,
            recordingState = recordingState
        )

        TipsCard()
    }

}