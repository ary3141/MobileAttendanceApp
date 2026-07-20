package com.example.mobilesurapp.UIApp.attendance.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Error
import androidx.compose.material.icons.rounded.Face
import androidx.compose.material.icons.rounded.HourglassTop
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mobilesurapp.UIApp.attendance.model.AttendanceStatus
import com.example.mobilesurapp.ui.theme.MobileSurAppTheme

@Composable
fun AttendanceStatusCard(
    status: AttendanceStatus,
    countdown: Int,
    modifier: Modifier = Modifier
) {

    val icon = when (status) {
        AttendanceStatus.Idle -> Icons.Rounded.Face
        AttendanceStatus.Collecting -> Icons.Rounded.Face
        AttendanceStatus.Verifying -> Icons.Rounded.HourglassTop
        is AttendanceStatus.Success -> Icons.Rounded.CheckCircle
        is AttendanceStatus.Failed -> Icons.Rounded.Error
    }

    val iconColor = when (status) {
        AttendanceStatus.Idle -> Color(0xFF34C759)
        AttendanceStatus.Collecting -> Color(0xFFFFB547)
        AttendanceStatus.Verifying -> Color(0xFF0A84FF)
        is AttendanceStatus.Success -> Color(0xFF34C759)
        is AttendanceStatus.Failed -> Color(0xFFFF453A)
    }

    val title = when (status) {
        AttendanceStatus.Idle -> "Looking for Face"
        AttendanceStatus.Collecting -> "Hold Still"
        AttendanceStatus.Verifying -> "Verifying Identity"
        is AttendanceStatus.Success -> "Attendance Recorded"
        is AttendanceStatus.Failed -> "Face Not Recognized"
    }

    val subtitle = when (status) {

        AttendanceStatus.Idle ->
            "Position your face inside the frame."

        AttendanceStatus.Collecting ->
            if (countdown > 0)
                "Capturing face... $countdown"
            else
                "Capturing face..."

        AttendanceStatus.Verifying ->
            "Checking your identity..."

        is AttendanceStatus.Success ->
            "Welcome back, ${status.employeeName}"

        is AttendanceStatus.Failed ->
            status.message
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1B1B1F)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(56.dp)
                    .background(
                        iconColor.copy(alpha = .15f),
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {

                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconColor
                )

            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF9C9CA3)
                )

            }

        }

    }

}

@Preview(showBackground = true, backgroundColor = 0xFF0F1013)
@Composable
private fun AttendanceStatusCardPreview() {

    MobileSurAppTheme {

        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            AttendanceStatusCard(
                status = AttendanceStatus.Idle,
                countdown = 0
            )

            AttendanceStatusCard(
                status = AttendanceStatus.Collecting,
                countdown = 3
            )

            AttendanceStatusCard(
                status = AttendanceStatus.Verifying,
                countdown = 0
            )

            AttendanceStatusCard(
                status = AttendanceStatus.Success("Arya"),
                countdown = 0
            )

            AttendanceStatusCard(
                status = AttendanceStatus.Failed("Please try again."),
                countdown = 0
            )

        }

    }

}