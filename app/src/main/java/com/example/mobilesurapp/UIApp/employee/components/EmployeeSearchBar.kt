package com.example.mobilesurapp.UIApp.employee.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
@Composable
fun EmployeeSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier
            .fillMaxWidth(),
        singleLine = true,
        shape = MaterialTheme.shapes.extraLarge,
        placeholder = {
            Text("Search employee...")
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search"
            )
        }
    )

}

@Preview(showBackground = true)
@Composable
private fun EmployeeSearchBarPreview() {

    val query = remember {
        mutableStateOf("")
    }

    MaterialTheme {

        EmployeeSearchBar(
            query = query.value,
            onQueryChange = {
                query.value = it
            },
            modifier = Modifier.padding(16.dp)
        )

    }

}