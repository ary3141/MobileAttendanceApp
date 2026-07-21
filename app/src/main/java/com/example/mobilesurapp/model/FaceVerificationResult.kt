package com.example.mobilesurapp.model

data class FaceVerificationResult(
    val isMatch: Boolean,
    val matchedEmployee: Employee?,
    val distance: Float = -1f
)