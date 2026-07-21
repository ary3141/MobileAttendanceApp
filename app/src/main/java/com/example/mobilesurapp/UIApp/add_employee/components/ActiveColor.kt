package com.example.mobilesurapp.UIApp.add_employee.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.height
import androidx.compose.ui.tooling.preview.Preview

private val ActiveColor = Color(0xFF5B3DF5)
private val InactiveColor = Color(0xFFD9D9D9)

@Composable
fun StepIndicator(
    currentStep: Int,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 28.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        StepCircle(
            number = 1,
            active = currentStep >= 1
        )

        StepLine(
            state = when {
                currentStep == 1 -> LineState.GRADIENT
                currentStep > 1 -> LineState.ACTIVE
                else -> LineState.INACTIVE
            },
            modifier = Modifier.weight(1f)
        )

        StepCircle(
            number = 2,
            active = currentStep >= 2
        )

        StepLine(
            state = when {
                currentStep == 2 -> LineState.GRADIENT
                currentStep > 2 -> LineState.ACTIVE
                else -> LineState.INACTIVE
            },
            modifier = Modifier.weight(1f)
        )

        StepCircle(
            number = 3,
            active = currentStep >= 3
        )
    }
}

private enum class LineState {
    ACTIVE,
    INACTIVE,
    GRADIENT
}

@Composable
private fun StepLine(
    state: LineState,
    modifier: Modifier = Modifier
) {

    val brush = when (state) {

        LineState.ACTIVE ->
            Brush.horizontalGradient(
                listOf(
                    ActiveColor,
                    ActiveColor
                )
            )

        LineState.INACTIVE ->
            Brush.horizontalGradient(
                listOf(
                    InactiveColor,
                    InactiveColor
                )
            )

        LineState.GRADIENT ->
            Brush.horizontalGradient(
                listOf(
                    ActiveColor,
                    InactiveColor
                )
            )
    }

    Box(
        modifier = modifier
            .height(2.dp)
            .background(brush)
    )
}

@Composable
private fun StepCircle(
    number: Int,
    active: Boolean
) {

    Surface(
        modifier = Modifier.size(40.dp),
        shape = CircleShape,
        color = if (active) ActiveColor else InactiveColor,
        shadowElevation = 3.dp
    ) {

        Box(
            contentAlignment = Alignment.Center
        ) {

            Text(
                text = number.toString(),
                color = if (active) Color.White else Color.Black,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

        }

    }

}

@Preview(
    name = "Step 1",
    showBackground = true,
    backgroundColor = 0xFFFFFFFF
)
@Composable
private fun StepIndicatorStep1Preview() {
    MaterialTheme {
        StepIndicator(
            currentStep = 1
        )
    }
}

@Preview(
    name = "Step 2",
    showBackground = true,
    backgroundColor = 0xFFFFFFFF
)
@Composable
private fun StepIndicatorStep2Preview() {
    MaterialTheme {
        StepIndicator(
            currentStep = 2
        )
    }
}

@Preview(
    name = "Step 3",
    showBackground = true,
    backgroundColor = 0xFFFFFFFF
)
@Composable
private fun StepIndicatorStep3Preview() {
    MaterialTheme {
        StepIndicator(
            currentStep = 3
        )
    }
}