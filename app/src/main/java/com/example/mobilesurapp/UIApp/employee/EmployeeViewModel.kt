package com.example.mobilesurapp.UIApp.employee

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobilesurapp.model.Employee
import com.example.mobilesurapp.UIApp.employee.model.EmployeeUiState
import com.example.mobilesurapp.api.ApiResult
import com.example.mobilesurapp.domain.usecase.GetEmployeesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmployeeViewModel @Inject constructor(

    private val getEmployeesUseCase: GetEmployeesUseCase

) : ViewModel() {

    private var allEmployees = emptyList<Employee>()

    private val _uiState = MutableStateFlow(
        EmployeeUiState(
            isLoading = false,
            searchQuery = "",
            employees = allEmployees,
            error = null
        )
    )

    val uiState: StateFlow<EmployeeUiState> = _uiState.asStateFlow()

    init {
        loadEmployees()
    }
    private fun loadEmployees() {

        viewModelScope.launch {

            _uiState.update {
                it.copy(isLoading = true)
            }

            when(val result = getEmployeesUseCase()) {

                is ApiResult.Success -> {

                    allEmployees = result.data

                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            employees = allEmployees
                        )
                    }

                }

                is ApiResult.Error -> {

                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = result.exception.message
                        )
                    }

                }

                is ApiResult.Loading -> {}
            }

        }

    }

    fun onSearchQueryChanged(query: String) {

        val filteredEmployees =
            if (query.isBlank()) {
                allEmployees
            } else {
                allEmployees.filter {
                    it.name.contains(query, ignoreCase = true) ||
                            (it.employeeCode ?: "").contains(query, ignoreCase = true)
                }
            }

        _uiState.update {
            it.copy(
                searchQuery = query,
                employees = filteredEmployees
            )
        }

    }

}