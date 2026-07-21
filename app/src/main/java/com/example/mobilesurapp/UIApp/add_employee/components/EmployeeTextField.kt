package com.example.mobilesurapp.UIApp.add_employee.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.ui.text.font.FontWeight

@Composable
fun EmployeeTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    error: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    enabled: Boolean = true,
    singleLine: Boolean = true,
) {
    Column {

        Text(
            text = label,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,

            modifier = Modifier
                .fillMaxWidth()
                .height(58.dp),

            placeholder = {
                Text(
                    placeholder,
                    color = Color(0xFFB8BCC8)
                )
            },

            shape = RoundedCornerShape(16.dp),

            singleLine = true,

            keyboardOptions = keyboardOptions,

            colors = OutlinedTextFieldDefaults.colors(

                focusedBorderColor = Color(0xFFD4D8E2),
                unfocusedBorderColor = Color(0xFFD4D8E2),

                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,

                cursorColor = MaterialTheme.colorScheme.primary
            )
        )
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFFFF
)
@Composable
private fun EmployeeTextFieldPreview() {

    MaterialTheme {

        EmployeeTextField(
            value = "",
            onValueChange = {},
            label = "Full Name",
            placeholder = "Enter employee name"
        )

    }

}