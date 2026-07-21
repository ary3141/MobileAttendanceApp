package com.example.mobilesurapp.UIApp.add_employee.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ReviewInfoCard(
    fullName: String,
    employeeId: String,
    email: String,
    phoneNumber: String,
    onEditClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(16.dp),
                clip = false
            ),
        shape = RoundedCornerShape(16.dp),
        color = Color.White
    ) {

        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Text(
                text = "Employee Information",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            ReviewItem(
                label = "Full Name",
                value = fullName
            )

            ReviewItem(
                label = "Employee ID",
                value = employeeId
            )

            ReviewItem(
                label = "Email",
                value = email
            )

            ReviewItem(
                label = "Phone Number",
                value = phoneNumber
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {

                TextButton(
                    onClick = onEditClick
                ) {

                    Icon(
                        imageVector = Icons.Outlined.Edit,
                        contentDescription = null
                    )

                    Text("Edit")

                }

            }

        }

    }

}

@Composable
private fun ReviewItem(
    label: String,
    value: String
) {

    Column {

        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = Color.Gray
        )

        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium
        )

    }

}

@Preview(showBackground = true)
@Composable
private fun ReviewInfoCardPreview() {

    MaterialTheme {

        ReviewInfoCard(
            fullName = "John Doe",
            employeeId = "EMP001",
            email = "john@email.com",
            phoneNumber = "08123456789",
            onEditClick = {}
        )

    }

}