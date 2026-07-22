package com.example.mobilesurapp.UIApp.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobilesurapp.UIApp.dashboard.domain.usecase.GetDashboardSummaryUseCase
import com.example.mobilesurapp.UIApp.dashboard.model.ConnectionState
import com.example.mobilesurapp.UIApp.dashboard.model.SystemStatus
import com.example.mobilesurapp.api.ApiResult
import com.example.mobilesurapp.domain.usecase.GetUserProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import com.example.mobilesurapp.UIApp.dashboard.model.DashboardUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(

    private val getProfileUseCase: GetUserProfileUseCase,

    private val getDashboardSummaryUseCase: GetDashboardSummaryUseCase

) : ViewModel() {

    private val _uiState =
        MutableStateFlow(DashboardUiState())

    val uiState = _uiState.asStateFlow()

    fun loadDashboardSummary() {

        viewModelScope.launch {

            when(val result = getDashboardSummaryUseCase()) {

                is ApiResult.Success -> {

                    _uiState.update {

                        it.copy(

                        )

                    }

                }

                else -> {}

            }

        }

    }
    fun loadProfile(adminId: Int) {

        viewModelScope.launch {

            _uiState.update {

                it.copy(
                    isLoading = true
                )

            }

            when (val result = getProfileUseCase(adminId)) {

                is ApiResult.Success -> {

                    _uiState.update {

                        it.copy(

                            username = result.data.name,

                            role = result.data.role,

                            systemStatus = getSystemStatus(),

                            isLoading = false

                        )

                    }

                }

                is ApiResult.Error -> {

                    _uiState.update {

                        it.copy(

                            error = result.exception.message,

                            isLoading = false

                        )

                    }

                }

                ApiResult.Loading -> {}

            }

        }

    }

    private fun getSystemStatus(): SystemStatus {

        return SystemStatus(

            camera = ConnectionState.ONLINE,

            server = ConnectionState.ONLINE,

            firebase = ConnectionState.CONNECTING,

            lastSync = "--",

            pendingUploads = 0

        )

    }

}