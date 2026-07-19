package com.example.mobilesurapp.UIApp.components.background

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.mobilesurapp.R

@Composable
fun DecorativeBlob(
    modifier: Modifier = Modifier,
    alpha: Float = 0.1f
) {
    Image(
        painter = painterResource(R.drawable.blob),
        contentDescription = null,
        modifier = modifier,
        alpha = alpha
    )
}