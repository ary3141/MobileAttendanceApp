package com.example.mobilesurapp.UIApp.add_employee.screens

import android.graphics.Bitmap
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
import com.example.mobilesurapp.UIApp.add_employee.components.FacePreviewCard
import com.example.mobilesurapp.UIApp.add_employee.components.ReviewInfoCard
import com.example.mobilesurapp.UIApp.add_employee.components.StepIndicator
import com.example.mobilesurapp.UIApp.components.GradientPrimaryButton

@Composable
fun ReviewStep(
    fullName: String,
    employeeId: String,
    email: String,
    phoneNumber: String,

    faceBitmap: Bitmap?,

    onEditClick: () -> Unit,
    onRetakeClick: () -> Unit,
    onRegisterClick: () -> Unit,

    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp)
            .navigationBarsPadding(),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        Spacer(modifier = Modifier.height(20.dp))

        StepIndicator(
            currentStep = 3
        )

        ReviewInfoCard(
            fullName = fullName,
            employeeId = employeeId,
            email = email,
            phoneNumber = phoneNumber,
            onEditClick = onEditClick
        )

        FacePreviewCard(
            faceBitmap = faceBitmap,
            onRetakeClick = onRetakeClick
        )

        GradientPrimaryButton(
            text = "Register Employee",
            onClick = onRegisterClick,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

    }

}

@Preview(showBackground = true)
@Composable
private fun ReviewStepPreview() {

    MaterialTheme {

        ReviewStep(
            fullName = "John Doe",
            employeeId = "EMP001",
            email = "john@example.com",
            phoneNumber = "08123456789",
            faceBitmap = null,
            onEditClick = {},
            onRetakeClick = {},
            onRegisterClick = {}
        )

    }

}