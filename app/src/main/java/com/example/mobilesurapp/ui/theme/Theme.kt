package com.example.mobilesurapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(

    primary = Primary,
    onPrimary = Surface,

    secondary = PrimaryVariant,
    onSecondary = Surface,

    background = Background,
    onBackground = TextPrimary,

    surface = Surface,
    onSurface = TextPrimary,

    error = Error,
    onError = Surface,

    outline = Border
)

private val DarkColors = darkColorScheme(
    primary = Primary,
    secondary = PrimaryVariant
)

@Composable
fun MobileSurAppTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {

    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = Typography,
        shapes = AppShapes,
        content = content
    )
}