package com.example.mobilesurapp.UIApp.dashboard

import androidx.compose.runtime.Composable

@Composable
fun DashboardRoute(

    onAttendanceClick: () -> Unit,

    onEmployeeClick: () -> Unit,

    onProfileClick: () -> Unit

) {

    DashboardScreen(

        username = "Admin",

        role = "Administrator",

        onAttendanceClick = onAttendanceClick,

        onEmployeeClick = onEmployeeClick,

        onSettingsClick = onProfileClick

    )

}