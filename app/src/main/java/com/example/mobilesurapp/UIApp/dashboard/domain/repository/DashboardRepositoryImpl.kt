package com.example.mobilesurapp.UIApp.dashboard.data.repository

import com.example.mobilesurapp.UIApp.dashboard.domain.model.DashboardSummary
import com.example.mobilesurapp.UIApp.dashboard.domain.repository.DashboardRepository
import com.example.mobilesurapp.api.ApiResult
import com.example.mobilesurapp.api.WebSocketService
import javax.inject.Inject

class DashboardRepositoryImpl @Inject constructor(

    private val webSocketService: WebSocketService

) : DashboardRepository {

    override suspend fun getDashboardSummary(): ApiResult<DashboardSummary> {

        return when (val result = webSocketService.getDashboardSummary()) {

            is ApiResult.Success -> {

                ApiResult.Success(
                    DashboardSummary(
                        totalEmployees = result.data.totalEmployees,
                        attendanceToday = result.data.attendanceToday
                    )
                )

            }

            is ApiResult.Error -> result

            ApiResult.Loading -> ApiResult.Loading
        }
    }
}