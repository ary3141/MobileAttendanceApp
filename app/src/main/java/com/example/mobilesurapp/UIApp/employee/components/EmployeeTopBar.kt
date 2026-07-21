package com.example.mobilesurapp.UIApp.employee.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.mobilesurapp.ui.theme.Primary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmployeeTopBar(
    onBackClick: () -> Unit,
    onAddEmployeeClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    CenterAlignedTopAppBar(
        modifier = modifier
            .fillMaxWidth(),
        title = {
            Text(
                text = "Employee Record",
                style = MaterialTheme.typography.titleMedium
            )
        },
        navigationIcon = {

            IconButton(
                onClick = onBackClick
            ) {

                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )

            }

        },
        actions = {

            IconButton(
                onClick = onAddEmployeeClick
            ) {

                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Employee"
                )

            }

        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )

}

@Preview(showBackground = true)
@Composable
private fun EmployeeTopBarPreview() {

    MaterialTheme {

        EmployeeTopBar(
            onBackClick = {},
            onAddEmployeeClick = {}
        )

    }

}