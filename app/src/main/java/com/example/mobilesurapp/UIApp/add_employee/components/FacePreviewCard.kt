package com.example.mobilesurapp.UIApp.add_employee.components

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Refresh
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun FacePreviewCard(
    faceBitmap: Bitmap?,
    onRetakeClick: () -> Unit,
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
                text = "Captured Face",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(2.5f),
                contentAlignment = Alignment.Center
            ) {

                if (faceBitmap != null) {

                    Image(
                        bitmap = faceBitmap.asImageBitmap(),
                        contentDescription = "Captured Face",
                        modifier = Modifier.fillMaxWidth()
                    )

                } else {

                    Text(
                        text = "No face captured",
                        color = Color.Gray
                    )

                }

            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {

                TextButton(
                    onClick = onRetakeClick
                ) {

                    Icon(
                        imageVector = Icons.Outlined.Refresh,
                        contentDescription = null
                    )

                    Text("Retake")

                }

            }

        }

    }

}

@Preview(showBackground = true)
@Composable
private fun FacePreviewCardPreview() {

    MaterialTheme {

        FacePreviewCard(
            faceBitmap = null,
            onRetakeClick = {}
        )

    }

}