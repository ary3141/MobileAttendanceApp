package com.example.mobilesurapp.UIApp.employee.model

data class EmployeeUiState(

    val isLoading: Boolean = false,

    val searchQuery: String = "",

    val employees: List<Employee> = emptyList(),

    val error: String? = null

)