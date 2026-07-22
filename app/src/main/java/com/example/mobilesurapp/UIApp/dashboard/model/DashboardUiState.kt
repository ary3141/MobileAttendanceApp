package com.example.mobilesurapp.UIApp.dashboard.model

data class DashboardUiState(

    val username: String = "",

    val role: String = "",

    val systemStatus: SystemStatus = SystemStatus(),

    val isLoading: Boolean = false,

    val error: String? = null

)