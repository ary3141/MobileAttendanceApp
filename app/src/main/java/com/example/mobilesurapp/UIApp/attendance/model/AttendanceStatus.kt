package com.example.mobilesurapp.UIApp.attendance.model

sealed class AttendanceStatus {

    object Idle : AttendanceStatus()

    object Collecting : AttendanceStatus()

    object Verifying : AttendanceStatus()

    data class Success(
        val employeeName: String
    ) : AttendanceStatus()

    data class Failed(
        val message: String
    ) : AttendanceStatus()
}