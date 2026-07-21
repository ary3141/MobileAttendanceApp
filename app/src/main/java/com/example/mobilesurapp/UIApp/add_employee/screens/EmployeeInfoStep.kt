package com.example.mobilesurapp.UIApp.add_employee.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mobilesurapp.UIApp.add_employee.components.EmployeeForm
import com.example.mobilesurapp.UIApp.add_employee.components.StepIndicator
import com.example.mobilesurapp.UIApp.components.GradientPrimaryButton

@Composable
fun EmployeeInfoStep(
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

    onNextClick: () -> Unit,

    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp)
            .navigationBarsPadding()
    ) {

        Spacer(modifier = Modifier.height(20.dp))

        StepIndicator(
            currentStep = 1
        )

        Spacer(modifier = Modifier.height(48.dp))

        EmployeeForm(
            fullName = fullName,
            employeeId = employeeId,
            email = email,
            phoneNumber = phoneNumber,

            onFullNameChange = onFullNameChange,
            onEmployeeIdChange = onEmployeeIdChange,
            onEmailChange = onEmailChange,
            onPhoneNumberChange = onPhoneNumberChange,

            fullNameError = fullNameError,
            employeeIdError = employeeIdError,
            emailError = emailError,
            phoneNumberError = phoneNumberError
        )

        Spacer(modifier = Modifier.weight(1f))

        GradientPrimaryButton(
            text = "Next",
            onClick = onNextClick,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

    }

}

@Preview(showBackground = true)
@Composable
private fun EmployeeInfoStepPreview() {

    MaterialTheme {

        EmployeeInfoStep(
            fullName = "",
            employeeId = "",
            email = "",
            phoneNumber = "",

            onFullNameChange = {},
            onEmployeeIdChange = {},
            onEmailChange = {},
            onPhoneNumberChange = {},

            onNextClick = {}
        )

    }

}

