package com.example.mobilesurapp.UIApp.dashboard.domain.repository

import com.example.mobilesurapp.UIApp.dashboard.domain.model.DashboardSummary
import com.example.mobilesurapp.api.ApiResult

interface DashboardRepository {

    suspend fun getDashboardSummary(): ApiResult<DashboardSummary>

}