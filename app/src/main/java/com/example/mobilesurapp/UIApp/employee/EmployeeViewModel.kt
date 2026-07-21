package com.example.mobilesurapp.UIApp.employee

import androidx.lifecycle.ViewModel
import com.example.mobilesurapp.UIApp.employee.model.Employee
import com.example.mobilesurapp.UIApp.employee.model.EmployeeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class EmployeeViewModel @Inject constructor() : ViewModel() {

    private val allEmployees = listOf(
        Employee(
            id = "EMP-001",
            fullName = "Arya Erlangga",
            email = "arya@email.com",
            phoneNumber = "08123456789"
        ),
        Employee(
            id = "EMP-002",
            fullName = "John Doe",
            email = "john@email.com",
            phoneNumber = "08123456780"
        ),
        Employee(
            id = "EMP-003",
            fullName = "Jane Smith",
            email = "jane@email.com",
            phoneNumber = "08123456781"
        ),
        Employee(
            id = "EMP-004",
            fullName = "Michael Johnson",
            email = "michael@email.com",
            phoneNumber = "08123456782"
        )
    )

    private val _uiState = MutableStateFlow(
        EmployeeUiState(
            isLoading = false,
            searchQuery = "",
            employees = allEmployees,
            error = null
        )
    )

    val uiState: StateFlow<EmployeeUiState> = _uiState.asStateFlow()

    fun onSearchQueryChanged(query: String) {

        val filteredEmployees =
            if (query.isBlank()) {
                allEmployees
            } else {
                allEmployees.filter {
                    it.fullName.contains(query, ignoreCase = true) ||
                            it.id.contains(query, ignoreCase = true)
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