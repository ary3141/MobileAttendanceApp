package com.example.mobilesurapp.UIApp.add_employee.model

import android.util.Size
import androidx.camera.core.CameraSelector
import com.google.mediapipe.tasks.vision.facedetector.FaceDetectorResult

data class CameraState(

    val lensFacing: Int = CameraSelector.LENS_FACING_BACK,

    val imageSize: Size = Size(1,1),

    val detectionResult: FaceDetectorResult? = null,

    val isFaceDetected: Boolean = false

)