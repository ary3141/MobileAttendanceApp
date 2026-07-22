package com.example.mobilesurapp.UIApp.dashboard

import com.example.mobilesurapp.UIApp.dashboard.model.SystemStatus

data class DashboardUiState(

    val username: String = "",

    val role: String = "",

    val totalEmployees: Int = 0,

    val attendanceToday: Int = 0,

    val systemStatus: SystemStatus = SystemStatus(),

    val isLoading: Boolean = false,

    val error: String? = null

)