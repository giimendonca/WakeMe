package com.wakeme.app.data.preferences

data class AppearancePreferences(
    val theme: ThemePreference = ThemePreference.SYSTEM,
    val accentColor: AccentColor = AccentColor.PURPLE
)
