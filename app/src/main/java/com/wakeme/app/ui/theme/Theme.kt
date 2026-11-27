package com.wakeme.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.wakeme.app.data.preferences.AccentColor

@Composable
fun WakeMeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    accentColor: AccentColor = AccentColor.PURPLE,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = wakeMeColorScheme(darkTheme, accentColor),
        typography = Typography,
        content = content
    )
}
