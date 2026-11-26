package com.wakeme.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = WakeMePalette.DarkPrimary,
    onPrimary = Color(0xFF28145E),
    secondary = WakeMePalette.DarkSecondary,
    onSecondary = Color(0xFF4D102F),
    tertiary = WakeMePalette.DarkAccent,
    onTertiary = Color(0xFF003738),
    background = WakeMePalette.DarkBackground,
    onBackground = WakeMePalette.DarkText,
    surface = WakeMePalette.DarkSurface,
    onSurface = WakeMePalette.DarkText
)

private val LightColorScheme = lightColorScheme(
    primary = WakeMePalette.LightPrimary,
    onPrimary = Color.White,
    secondary = WakeMePalette.LightSecondary,
    onSecondary = Color(0xFF4A1028),
    tertiary = WakeMePalette.LightAccent,
    onTertiary = Color(0xFF003738),
    background = WakeMePalette.LightBackground,
    onBackground = WakeMePalette.LightText,
    surface = WakeMePalette.LightSurface,
    onSurface = WakeMePalette.LightText
)

@Composable
fun WakeMeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
        typography = Typography,
        content = content
    )
}
