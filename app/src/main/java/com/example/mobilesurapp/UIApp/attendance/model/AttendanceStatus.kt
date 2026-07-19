package com.example.mobilesurapp.UIApp.attendance.model

/**
 * Represents the current attendance recognition state.
 */
sealed interface AttendanceStatus {

    /**
     * Camera is ready and waiting for a face.
     */
    data object Idle : AttendanceStatus

    /**
     * A face has been detected and is being analyzed.
     */
    data object Detecting : AttendanceStatus

    /**
     * Face successfully recognized.
     */
    data class Success(
        val employeeName: String
    ) : AttendanceStatus

    /**
     * Face could not be recognized.
     */
    data object Failed : AttendanceStatus
}