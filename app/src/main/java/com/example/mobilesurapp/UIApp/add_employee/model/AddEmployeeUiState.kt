package com.example.mobilesurapp.UIApp.add_employee.model

import android.graphics.Bitmap

data class AddEmployeeUiState(

    // Wizard
    val currentStep: AddEmployeeStep = AddEmployeeStep.INFORMATION,

    // Form
    val fullName: String = "",
    val employeeId: String = "",
    val email: String = "",
    val phoneNumber: String = "",

    // Validation
    val fullNameError: String? = null,
    val employeeIdError: String? = null,
    val emailError: String? = null,
    val phoneNumberError: String? = null,

    // Camera
    val isFaceDetected: Boolean = false,
    val recordingState: RecordingState = RecordingState.IDLE,
    val recordingProgress: Float = 0f,
    val countdown: Int = 5,

    // Review
    val capturedFace: Bitmap? = null,

    // Registration
    val isLoading: Boolean = false,
    val success: Boolean = false,
    val error: String? = null

)