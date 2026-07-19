package com.example.mobilesurapp.UIApp.attendance.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ExitToApp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mobilesurapp.ui.theme.MobileSurAppTheme

@Composable
fun AttendanceHeader(
    modifier: Modifier = Modifier,
    onExitClick: () -> Unit
) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = "Attendance",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        IconButton(
            modifier = Modifier.align(Alignment.CenterEnd),
            onClick = onExitClick
        ) {

            Icon(
                imageVector = Icons.AutoMirrored.Rounded.ExitToApp,
                contentDescription = null,
                tint = Color.White
            )

        }

    }

}

@Preview(showBackground = true, backgroundColor = 0xFF111111)
@Composable
private fun AttendanceHeaderPreview() {

    MobileSurAppTheme {

        AttendanceHeader(
            onExitClick = {}
        )

    }

}