package com.example.mobilesurapp.UIApp.employee

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun EmployeeRoute(
    onBackClick: () -> Unit,
    onAddEmployeeClick: () -> Unit,
    onEmployeeClick: (String) -> Unit
) {

    val viewModel: EmployeeViewModel = hiltViewModel()

    val uiState by viewModel.uiState.collectAsState()

    EmployeeScreen(
        uiState = uiState,
        onSearchQueryChange = viewModel::onSearchQueryChanged,
        onBackClick = onBackClick,
        onAddEmployeeClick = onAddEmployeeClick,
        onEmployeeClick = { employee ->
            onEmployeeClick(employee.id)
        }
    )

}