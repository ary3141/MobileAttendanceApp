package com.example.mobilesurapp.UIApp.dashboard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mobilesurapp.UIApp.components.background.DashboardBackground
import com.example.mobilesurapp.UIApp.dashboard.components.DashboardPanel
import com.example.mobilesurapp.UIApp.dashboard.components.StatusCard
import com.example.mobilesurapp.ui.theme.MobileSurAppTheme
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun DashboardScreen(

    username: String = "Admin",
    role: String = "Administrator",

    onAttendanceClick: () -> Unit = {},
    onEmployeeClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {}

) {

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        DashboardBackground()

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {

            Spacer(modifier = Modifier.height(80.dp))

            GreetingSection(
                username = username + " 👋",
                role = role,
                modifier = Modifier.padding(horizontal = 1.dp)
            )

            Spacer(modifier = Modifier.height(1.dp))

            DashboardPanel(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(
                        horizontal = 16.dp,
                        vertical = 50.dp
                    )
            ) {

                DashboardGrid(
                    onAttendanceClick = onAttendanceClick,
                    onEmployeeClick = onEmployeeClick,
                    onSettingsClick = onSettingsClick
                )

                Spacer(modifier = Modifier.height(24.dp))

                StatusCard()

            }

        }

    }

}

@Preview(showSystemUi = true)
@Composable
private fun DashboardPreview() {

    MobileSurAppTheme {

        DashboardScreen()

    }

}