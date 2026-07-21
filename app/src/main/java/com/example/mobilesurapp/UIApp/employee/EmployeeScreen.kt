package com.example.mobilesurapp.UIApp.employee

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mobilesurapp.UIApp.employee.components.EmployeeCard
import com.example.mobilesurapp.UIApp.employee.components.EmployeeEmptyState
import com.example.mobilesurapp.UIApp.employee.components.EmployeeSearchBar
import com.example.mobilesurapp.UIApp.employee.components.EmployeeTopBar
import com.example.mobilesurapp.UIApp.employee.model.Employee
import com.example.mobilesurapp.UIApp.employee.model.EmployeeUiState

@Composable
fun EmployeeScreen(
    uiState: EmployeeUiState,
    onSearchQueryChange: (String) -> Unit,
    onBackClick: () -> Unit,
    onAddEmployeeClick: () -> Unit,
    onEmployeeClick: (Employee) -> Unit,
    modifier: Modifier = Modifier
) {

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {

            EmployeeTopBar(
                onBackClick = onBackClick,
                onAddEmployeeClick = onAddEmployeeClick
            )

        }

    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp)
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            EmployeeSearchBar(
                query = uiState.searchQuery,
                onQueryChange = onSearchQueryChange
            )

            Spacer(modifier = Modifier.height(20.dp))

            when {

                uiState.isLoading -> {

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {

                        CircularProgressIndicator()

                    }

                }

                uiState.employees.isEmpty() -> {

                    EmployeeEmptyState(
                        modifier = Modifier.fillMaxSize()
                    )

                }

                else -> {

                    LazyColumn(
                        contentPadding = PaddingValues(bottom = 24.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {

                        items(
                            items = uiState.employees,
                            key = { it.id }
                        ) { employee ->

                            EmployeeCard(
                                employee = employee,
                                onClick = {
                                    onEmployeeClick(employee)
                                }
                            )

                        }

                    }

                }

            }

        }

    }

}

@Preview(showBackground = true)
@Composable
private fun EmployeeScreenPreview() {

    MaterialTheme {

        EmployeeScreen(

            uiState = EmployeeUiState(

                employees = listOf(

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
                    )

                )

            ),

            onSearchQueryChange = {},

            onBackClick = {},

            onAddEmployeeClick = {},

            onEmployeeClick = {}

        )

    }

}