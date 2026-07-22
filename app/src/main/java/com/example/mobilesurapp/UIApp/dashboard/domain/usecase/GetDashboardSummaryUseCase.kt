package com.example.mobilesurapp.UIApp.dashboard.domain.usecase

import com.example.mobilesurapp.UIApp.dashboard.domain.repository.DashboardRepository
import javax.inject.Inject

class GetDashboardSummaryUseCase @Inject constructor(

    private val repository: DashboardRepository

) {

    suspend operator fun invoke() =
        repository.getDashboardSummary()

}