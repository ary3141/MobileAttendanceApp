package com.example.mobilesurapp.UIApp.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Badge
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mobilesurapp.UIApp.dashboard.components.AttendanceCard
import com.example.mobilesurapp.UIApp.dashboard.components.DashboardInfoCard
import com.example.mobilesurapp.ui.theme.MobileSurAppTheme

@Composable
fun DashboardGrid(
    onAttendanceClick: () -> Unit,
    onEmployeeClick: () -> Unit,
    onSettingsClick: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(320.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        AttendanceCard(
            modifier = Modifier.weight(1f),
            onClick = onAttendanceClick
        )

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            DashboardInfoCard(
                modifier = Modifier.weight(1.2f),
                title = "Employee Record",
                icon = Icons.Rounded.Badge,
                iconColor = Color(0xFFFF9800),
                onClick = onEmployeeClick
            )

            DashboardInfoCard(
                modifier = Modifier.weight(1.1f),
                title = "Settings",
                icon = Icons.Rounded.Settings,
                iconColor = Color(0xFF4CAF50),
                onClick = onSettingsClick
            )

        }

    }

}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun DashboardGridPreview() {

    MobileSurAppTheme {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {

            DashboardGrid(
                onAttendanceClick = {},
                onEmployeeClick = {},
                onSettingsClick = {}
            )

        }

    }

}