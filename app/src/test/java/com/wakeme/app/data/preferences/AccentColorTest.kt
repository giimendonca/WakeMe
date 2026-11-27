package com.wakeme.app.data.preferences

import org.junit.Assert.assertEquals
import org.junit.Test

class AccentColorTest {
    @Test
    fun storedAccentIsRestoredForEveryPaletteOption() {
        AccentColor.entries.forEach { color ->
            assertEquals(color, AccentColor.fromStoredValue(color.name))
        }
    }

    @Test
    fun missingOrUnknownAccentUsesWakeMePurple() {
        assertEquals(AccentColor.PURPLE, AccentColor.fromStoredValue(null))
        assertEquals(AccentColor.PURPLE, AccentColor.fromStoredValue("unknown"))
    }
}
