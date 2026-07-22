package com.example.mobilesurapp.UIApp.dashboard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mobilesurapp.UIApp.login.LoginStateViewModel

@Composable
fun DashboardRoute(

    onAttendanceClick: () -> Unit,

    onEmployeeClick: () -> Unit,

    onProfileClick: () -> Unit,

    loginStateViewModel: LoginStateViewModel,

    viewModel: DashboardViewModel = hiltViewModel()

) {

    val uiState by viewModel.uiState.collectAsState()

    val adminId by loginStateViewModel.currentAdminId.collectAsState()

    LaunchedEffect(adminId) {

        adminId
            ?.toIntOrNull()
            ?.let { id ->
                viewModel.loadProfile(id)
                viewModel.loadDashboardSummary()
            }

    }

    DashboardScreen(

        username = uiState.username,

        role = uiState.role,

        systemStatus = uiState.systemStatus,

        onAttendanceClick = onAttendanceClick,

        onEmployeeClick = onEmployeeClick,

        onSettingsClick = onProfileClick

    )

}