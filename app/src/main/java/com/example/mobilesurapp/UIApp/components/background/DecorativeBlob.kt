package com.example.mobilesurapp.UIApp.components.background

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mobilesurapp.R
import com.example.mobilesurapp.ui.theme.MobileSurAppTheme

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

@Preview(showBackground = true)
@Composable
fun BlobPreview() {
    MobileSurAppTheme {
        DecorativeBlob(
            modifier = Modifier.size(200.dp),
            alpha = 1f
        )
    }
}