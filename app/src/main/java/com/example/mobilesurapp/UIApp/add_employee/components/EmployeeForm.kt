package com.example.mobilesurapp.UIApp.add_employee.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun EmployeeForm(
    fullName: String,
    employeeId: String,
    email: String,
    phoneNumber: String,

    onFullNameChange: (String) -> Unit,
    onEmployeeIdChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPhoneNumberChange: (String) -> Unit,

    fullNameError: String? = null,
    employeeIdError: String? = null,
    emailError: String? = null,
    phoneNumberError: String? = null,

    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        EmployeeTextField(
            value = fullName,
            onValueChange = onFullNameChange,
            label = "Full Name",
            placeholder = "Enter full name",
            error = fullNameError
        )

        EmployeeTextField(
            value = employeeId,
            onValueChange = onEmployeeIdChange,
            label = "Employee ID",
            placeholder = "Enter employee ID",
            error = employeeIdError
        )

        EmployeeTextField(
            value = email,
            onValueChange = onEmailChange,
            label = "Email",
            placeholder = "example@gmail.com",
            error = emailError,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            )
        )

        EmployeeTextField(
            value = phoneNumber,
            onValueChange = onPhoneNumberChange,
            label = "Phone Number",
            placeholder = "08xxxxxxxxxx",
            error = phoneNumberError,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone
            )
        )

    }

}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFFFF
)
@Composable
private fun EmployeeFormPreview() {

    MaterialTheme {

        EmployeeForm(
            fullName = "",
            employeeId = "",
            email = "",
            phoneNumber = "",

            onFullNameChange = {},
            onEmployeeIdChange = {},
            onEmailChange = {},
            onPhoneNumberChange = {}
        )

    }

}