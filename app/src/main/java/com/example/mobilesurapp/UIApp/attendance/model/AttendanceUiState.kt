package com.example.mobilesurapp.UIApp.attendance.model

data class AttendanceUiState(

    val status: AttendanceStatus = AttendanceStatus.Idle,

    val cameraState: CameraState = CameraState.FRONT,

    val isCameraReady: Boolean = false,

    val isFaceDetected: Boolean = false,

    val employeeName: String? = null,

    val employeeCode: String? = null,

    val similarity: Float? = null,

    val isVerifying: Boolean = false,

    val attendanceTime: String? = null,

    val attendanceDate: String? = null,

    val countdown: Int = 0
)