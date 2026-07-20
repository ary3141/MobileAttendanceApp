package com.example.mobilesurapp.UIApp.attendance.model

sealed interface AttendanceStatus {

    data object Idle : AttendanceStatus

    data object Collecting : AttendanceStatus

    data object Verifying : AttendanceStatus

    data class Success(
        val employeeName: String
    ) : AttendanceStatus

    data class Failed(
        val message: String = "Face not recognized"
    ) : AttendanceStatus
}