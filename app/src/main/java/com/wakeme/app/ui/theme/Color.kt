package com.wakeme.app.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.graphics.luminance
import com.wakeme.app.data.preferences.AccentColor

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

    fun accent(color: AccentColor): Color = when (color) {
        AccentColor.PURPLE -> Color(0xFF7657E8)
        AccentColor.PINK -> Color(0xFFFF719A)
        AccentColor.TURQUOISE -> Color(0xFF36BFC3)
        AccentColor.BLUE -> Color(0xFF4285E8)
        AccentColor.GREEN -> Color(0xFF36A879)
    }
}

fun wakeMeColorScheme(darkTheme: Boolean, accentColor: AccentColor) = run {
    val background = if (darkTheme) WakeMePalette.DarkBackground else WakeMePalette.LightBackground
    val surface = if (darkTheme) WakeMePalette.DarkSurface else WakeMePalette.LightSurface
    val text = if (darkTheme) WakeMePalette.DarkText else WakeMePalette.LightText
    val secondary = if (darkTheme) WakeMePalette.DarkSecondary else WakeMePalette.LightSecondary
    val tertiary = if (darkTheme) WakeMePalette.DarkAccent else WakeMePalette.LightAccent
    val rawAccent = WakeMePalette.accent(accentColor)
    val primary = accessibleAccent(rawAccent, background, darkTheme)
    val onPrimary = if (darkTheme) Color(0xFF11111B) else Color.White
    val primaryContainer = rawAccent.copy(alpha = if (darkTheme) 0.22f else 0.12f)
        .compositeOver(if (darkTheme) surface else background)

    if (darkTheme) {
        androidx.compose.material3.darkColorScheme(
            primary = primary,
            onPrimary = onPrimary,
            primaryContainer = primaryContainer,
            onPrimaryContainer = text,
            secondary = secondary,
            onSecondary = Color(0xFF4D102F),
            tertiary = tertiary,
            onTertiary = Color(0xFF003738),
            background = background,
            onBackground = text,
            surface = surface,
            onSurface = text
        )
    } else {
        androidx.compose.material3.lightColorScheme(
            primary = primary,
            onPrimary = onPrimary,
            primaryContainer = primaryContainer,
            onPrimaryContainer = text,
            secondary = secondary,
            onSecondary = Color(0xFF4A1028),
            tertiary = tertiary,
            onTertiary = Color(0xFF003738),
            background = background,
            onBackground = text,
            surface = surface,
            onSurface = text
        )
    }
}

private fun accessibleAccent(color: Color, background: Color, darkTheme: Boolean): Color {
    val target = if (darkTheme) Color.White else Color.Black
    val onPrimary = if (darkTheme) Color(0xFF11111B) else Color.White
    for (step in 0..100) {
        val fraction = step / 100f
        val candidate = Color(
            red = color.red + (target.red - color.red) * fraction,
            green = color.green + (target.green - color.green) * fraction,
            blue = color.blue + (target.blue - color.blue) * fraction,
            alpha = color.alpha
        )
        if (contrastRatio(candidate, background) >= 4.5f &&
            contrastRatio(candidate, onPrimary) >= 4.5f
        ) {
            return candidate
        }
    }
    return target
}

private fun contrastRatio(first: Color, second: Color): Float {
    val lighter = maxOf(first.luminance(), second.luminance())
    val darker = minOf(first.luminance(), second.luminance())
    return (lighter + 0.05f) / (darker + 0.05f)
}
