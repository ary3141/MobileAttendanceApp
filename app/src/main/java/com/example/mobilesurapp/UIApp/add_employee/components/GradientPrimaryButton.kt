package com.example.mobilesurapp.UIApp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun GradientPrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    showArrow: Boolean = true
) {

    val gradient = Brush.horizontalGradient(
        colors = listOf(
            Color(0xFF6A4DFF),
            Color(0xFF5B3DF5)
        )
    )

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        shadowElevation = 6.dp
    ) {

        Box(
            modifier = Modifier
                .background(
                    if (enabled) gradient
                    else Brush.horizontalGradient(
                        listOf(
                            Color.LightGray,
                            Color.Gray
                        )
                    )
                )
                .clip(RoundedCornerShape(16.dp))
                .clickable(
                    enabled = enabled,
                    onClick = onClick
                )
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 20.dp),
        ) {

            Text(
                text = text,
                color = Color.White,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.align(Alignment.Center)
            )

            if (showArrow) {

                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.align(Alignment.CenterEnd)
                )

            }

        }

    }

}

@Preview(showBackground = true)
@Composable
private fun GradientPrimaryButtonPreview() {

    MaterialTheme {

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(24.dp)
        ) {

            GradientPrimaryButton(
                text = "Next",
                onClick = {}
            )

            GradientPrimaryButton(
                text = "Capture",
                onClick = {}
            )

            GradientPrimaryButton(
                text = "Register Employee",
                onClick = {},
                showArrow = false
            )

        }

    }

}