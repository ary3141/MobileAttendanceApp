package com.example.mobilesurapp.UIApp.employee.model

import com.example.mobilesurapp.model.Employee

data class EmployeeUiState(

    val isLoading: Boolean = false,

    val searchQuery: String = "",

    val employees: List<Employee> = emptyList(),

    val error: String? = null

)