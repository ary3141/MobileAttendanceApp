package com.example.mobilesurapp.UIApp.attendance.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import com.example.mobilesurapp.UIApp.attendance.model.AttendanceStatus

@Composable
fun FaceGuide(
    status: AttendanceStatus,
    modifier: Modifier = Modifier
) {
    val guideColor = when (status) {

        AttendanceStatus.Idle ->
            Color.White

        AttendanceStatus.Collecting ->
            Color(0xFFFFB547)

        AttendanceStatus.Verifying ->
            Color(0xFF0A84FF)

        is AttendanceStatus.Success ->
            Color(0xFF34C759)

        is AttendanceStatus.Failed ->
            Color(0xFFFF453A)

    }
    Canvas(
        modifier = modifier.fillMaxSize()
    ) {

        val w = size.width
        val h = size.height

        // Bigger guide
        val guideWidth = w * 0.72f
        val guideHeight = h * 0.62f

        // Move slightly downward
        val left = (w - guideWidth) / 2f
        val top = (h - guideHeight) / 2f + h * 0.06f

        val right = left + guideWidth
        val bottom = top + guideHeight

        val cornerLength = 70f
        val strokeWidth = 18f

        // Top Left
        drawLine(
            color = guideColor,
            start = Offset(left, top + cornerLength),
            end = Offset(left, top),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )

        drawLine(
            color = guideColor,
            start = Offset(left, top),
            end = Offset(left + cornerLength, top),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )

        // Top Right
        drawLine(
            color = guideColor,
            start = Offset(right - cornerLength, top),
            end = Offset(right, top),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )

        drawLine(
            color = guideColor,
            start = Offset(right, top),
            end = Offset(right, top + cornerLength),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )

        // Bottom Left
        drawLine(
            color = guideColor,
            start = Offset(left, bottom - cornerLength),
            end = Offset(left, bottom),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )

        drawLine(
            color = guideColor,
            start = Offset(left, bottom),
            end = Offset(left + cornerLength, bottom),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )

        // Bottom Right
        drawLine(
            color = guideColor,
            start = Offset(right - cornerLength, bottom),
            end = Offset(right, bottom),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )

        drawLine(
            color = guideColor,
            start = Offset(right, bottom - cornerLength),
            end = Offset(right, bottom),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )
    }

}