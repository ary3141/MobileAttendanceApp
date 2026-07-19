package com.example.mobilesurapp.UIApp.components.background

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mobilesurapp.ui.theme.MobileSurAppTheme
import com.example.mobilesurapp.ui.theme.Primary
import androidx.compose.ui.Alignment

@Composable
fun DashboardBackground() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
                .background(
                    color = Primary,
                    shape = RoundedCornerShape(
                        bottomStart = 36.dp,
                        bottomEnd = 36.dp
                    )
                )
        )
        // Top Left Blob
        DecorativeBlob(
            modifier = Modifier
                .align(Alignment.TopStart)
                .size(500.dp)
                .offset(
                    x = (-220).dp,
                    y = (-180).dp
                )
                .rotate(35f),
            alpha = 0.18f
        )
        // Middle Right Blob
        DecorativeBlob(
            modifier = Modifier
                .align(Alignment.Center)
                .size(280.dp)
                .offset(
                    x = -120.dp,
                    y = (-100).dp
                )
                .rotate(120f),
            alpha = 0.10f
        )
        // Bottom Right Blob
        DecorativeBlob(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .size(700.dp)
                .offset(
                    x = 220.dp,
                    y = 270.dp
                )
                .rotate(210f),
            alpha = 0.08f
        )
    }
}


@Preview(
    name = "Dashboard",
    showSystemUi = true,
    showBackground = true
)
@Composable
fun DashboardBackgroundPreview() {
    MobileSurAppTheme {
        DashboardBackground()
    }
}