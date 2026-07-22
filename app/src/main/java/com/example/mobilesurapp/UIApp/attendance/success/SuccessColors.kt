package com.example.mobilesurapp.UIApp.attendance.success

import androidx.compose.ui.graphics.Color

/**
 * Colors used throughout the Attendance Success feature.
 *
 * Keeping them centralized makes the UI much easier to maintain.
 */
object SuccessColors {

    // ------------------------------------------------------------------------
    // Background
    // ------------------------------------------------------------------------

    val Background = Color(0xFF18241D)

    // ------------------------------------------------------------------------
    // Success
    // ------------------------------------------------------------------------

    val SuccessGreen = Color(0xFF16C47F)

    // ------------------------------------------------------------------------
    // Card
    // ------------------------------------------------------------------------

    val Card = Color.White

    val CardShadow = Color(0x22000000)

    // ------------------------------------------------------------------------
    // Countdown Card
    // ------------------------------------------------------------------------

    val CountdownCard = Color(0xFF1C1D21)

    val CountdownText = Color.White

    // ------------------------------------------------------------------------
    // Progress
    // ------------------------------------------------------------------------

    val ProgressTrack = Color(0xFF8F8F8F)

    val ProgressStart = Color(0xFF6C4DFF)

    val ProgressEnd = Color(0xFFA79CFF)

    // ------------------------------------------------------------------------
    // Greeting Banner
    // ------------------------------------------------------------------------

    val GreetingBackground = Color(0xFFE8DEFF)

    val GreetingText = Color(0xFF5A41FF)

    // ------------------------------------------------------------------------
    // Text
    // ------------------------------------------------------------------------

    val PrimaryText = Color(0xFF111111)

    val SecondaryText = Color(0xFFB6B6B6)

    val SubtitleText = Color(0xFF8E8E93)

    // ------------------------------------------------------------------------
    // Confetti
    // ------------------------------------------------------------------------

    val ConfettiYellow = Color(0xFFFFC107)

    val ConfettiGreen = Color(0xFF00E676)

    val ConfettiBlue = Color(0xFF2979FF)

    val ConfettiPink = Color(0xFFFF66CC)

    val ConfettiRed = Color(0xFFFF3D3D)

    /**
     * Pool of colors used by the confetti animation.
     */
    val ConfettiColors = listOf(
        ConfettiYellow,
        ConfettiGreen,
        ConfettiBlue,
        ConfettiPink,
        ConfettiRed
    )
}