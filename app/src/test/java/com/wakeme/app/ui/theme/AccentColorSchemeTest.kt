package com.wakeme.app.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import com.wakeme.app.data.preferences.AccentColor
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class AccentColorSchemeTest {
    @Test
    fun everyAccentHasReadableRolesInLightAndDarkThemes() {
        AccentColor.entries.forEach { accent ->
            listOf(false, true).forEach { darkTheme ->
                val scheme = wakeMeColorScheme(darkTheme, accent)
                assertContrastAtLeast(scheme.primary, scheme.background, 4.5f)
                assertContrastAtLeast(scheme.primary, scheme.onPrimary, 4.5f)
                assertContrastAtLeast(scheme.primaryContainer, scheme.onPrimaryContainer, 4.5f)
                assertEquals(
                    if (darkTheme) WakeMePalette.DarkBackground.toArgb() else WakeMePalette.LightBackground.toArgb(),
                    scheme.background.toArgb()
                )
            }
        }
    }

    @Test
    fun accentChoicesUseTheApprovedSwatchColorsAndProduceDistinctSchemes() {
        val expectedColors = mapOf(
            AccentColor.PURPLE to 0xFF7657E8,
            AccentColor.PINK to 0xFFFF719A,
            AccentColor.TURQUOISE to 0xFF36BFC3,
            AccentColor.BLUE to 0xFF4285E8,
            AccentColor.GREEN to 0xFF36A879
        )

        expectedColors.forEach { (accent, argb) ->
            assertEquals(argb.toInt(), WakeMePalette.accent(accent).toArgb())
        }

        val primaryColors = AccentColor.entries.map { wakeMeColorScheme(false, it).primary.toArgb() }
        assertEquals(primaryColors.size, primaryColors.distinct().size)
    }

    private fun assertContrastAtLeast(first: Color, second: Color, minimum: Float) {
        val lighter = maxOf(first.luminance(), second.luminance())
        val darker = minOf(first.luminance(), second.luminance())
        val ratio = (lighter + 0.05f) / (darker + 0.05f)
        assertTrue("Expected contrast >= $minimum, got $ratio", ratio >= minimum)
    }
}
