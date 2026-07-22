package com.example.mobilesurapp.UIApp.components.background

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mobilesurapp.ui.theme.MobileSurAppTheme
import com.example.mobilesurapp.ui.theme.Primary

@Composable
fun LoginBackground(
    content: @Composable BoxScope.() -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        DecorativeBlob(
            modifier = Modifier
                .align(Alignment.TopStart)
                .size(420.dp)
                .offset(
                    x = (-170).dp,
                    y = (-180).dp
                )
                .rotate(35f),
            alpha = 0.12f
        )

        DecorativeBlob(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .size(520.dp)
                .offset(
                    x = 220.dp,
                    y = 260.dp
                )
                .rotate(210f),
            alpha = 0.08f
        )

        DecorativeBlob(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .size(180.dp)
                .offset(
                    x = 90.dp,
                    y = (-160).dp
                )
                .rotate(120f),
            alpha = 0.06f
        )

        content()

    }

}

@Preview(
    name = "Login Background",
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun LoginBackgroundPreview() {
    MobileSurAppTheme {
        LoginBackground {
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(200.dp)
                    .background(Color.Red)
            )
        }
    }
}