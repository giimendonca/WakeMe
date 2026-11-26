package com.wakeme.app.ui.theme

import androidx.compose.ui.graphics.Color

object WakeMePalette {
    val LightBackground = Color(0xFFF8F7FC)
    val LightSurface = Color(0xFFFFFFFF)
    val LightPrimary = Color(0xFF7657E8)
    val LightSecondary = Color(0xFFFF719A)
    val LightAccent = Color(0xFF36BFC3)
    val LightText = Color(0xFF252238)

    val DarkBackground = Color(0xFF11111B)
    val DarkSurface = Color(0xFF1D1B2B)
    val DarkPrimary = Color(0xFFA78BFA)
    val DarkSecondary = Color(0xFFF472B6)
    val DarkAccent = Color(0xFF4DD6D0)
    val DarkText = Color(0xFFF5F1FF)

    val LightGradient = listOf(LightPrimary, LightSecondary, LightAccent)
    val DarkGradient = listOf(DarkPrimary, DarkSecondary, DarkAccent)
}
