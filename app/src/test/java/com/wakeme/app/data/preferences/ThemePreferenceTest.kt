package com.wakeme.app.data.preferences

import org.junit.Assert.assertEquals
import org.junit.Test

class ThemePreferenceTest {
    @Test
    fun storedPreferenceIsRestoredForKnownValues() {
        ThemePreference.entries.forEach { preference ->
            assertEquals(preference, ThemePreference.fromStoredValue(preference.name))
        }
    }

    @Test
    fun missingOrUnknownPreferenceUsesSystemDefault() {
        assertEquals(ThemePreference.SYSTEM, ThemePreference.fromStoredValue(null))
        assertEquals(ThemePreference.SYSTEM, ThemePreference.fromStoredValue("unknown"))
    }
}
