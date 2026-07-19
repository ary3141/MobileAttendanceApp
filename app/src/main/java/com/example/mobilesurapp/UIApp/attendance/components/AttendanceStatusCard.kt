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
    modifier: Modifier = Modifier
) {

    val icon = when (status) {
        AttendanceStatus.Idle -> Icons.Rounded.Face
        AttendanceStatus.Detecting -> Icons.Rounded.HourglassTop
        is AttendanceStatus.Success -> Icons.Rounded.CheckCircle
        AttendanceStatus.Failed -> Icons.Rounded.Error
    }

    val iconColor = when (status) {
        AttendanceStatus.Idle -> Color(0xFF34C759)
        AttendanceStatus.Detecting -> Color(0xFFFFB547)
        is AttendanceStatus.Success -> Color(0xFF34C759)
        AttendanceStatus.Failed -> Color(0xFFFF453A)
    }

    val title = when (status) {
        AttendanceStatus.Idle -> "Ready to Scan"
        AttendanceStatus.Detecting -> "Detecting Face"
        is AttendanceStatus.Success -> "Welcome ${status.employeeName}"
        AttendanceStatus.Failed -> "Face Not Recognized"
    }

    val subtitle = when (status) {
        AttendanceStatus.Idle ->
            "Position your face inside the guide."

        AttendanceStatus.Detecting ->
            "Hold still while verifying."

        is AttendanceStatus.Success ->
            "Attendance recorded successfully."

        AttendanceStatus.Failed ->
            "Please try again."
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
                status = AttendanceStatus.Idle
            )

            AttendanceStatusCard(
                status = AttendanceStatus.Detecting
            )

            AttendanceStatusCard(
                status = AttendanceStatus.Success("Arya")
            )

            AttendanceStatusCard(
                status = AttendanceStatus.Failed
            )

        }

    }

}