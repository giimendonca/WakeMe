package com.wakeme.app.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.themeDataStore by preferencesDataStore(name = "user_preferences")

class ThemePreferencesRepository(private val context: Context) {
    private val preferences = context.themeDataStore.data

    val appearancePreferences: Flow<AppearancePreferences> = preferences.map { values ->
        AppearancePreferences(
            theme = ThemePreference.fromStoredValue(values[THEME_KEY]),
            accentColor = AccentColor.fromStoredValue(values[ACCENT_COLOR_KEY])
        )
    }

    val themePreference: Flow<ThemePreference> = preferences.map { values ->
        ThemePreference.fromStoredValue(values[THEME_KEY])
    }

    suspend fun setThemePreference(themePreference: ThemePreference) {
        context.themeDataStore.edit { preferences ->
            preferences[THEME_KEY] = themePreference.name
        }
    }

    suspend fun setAccentColor(accentColor: AccentColor) {
        context.themeDataStore.edit { preferences ->
            preferences[ACCENT_COLOR_KEY] = accentColor.name
        }
    }

    private companion object {
        val THEME_KEY = stringPreferencesKey("theme_preference")
        val ACCENT_COLOR_KEY = stringPreferencesKey("accent_color")
    }
}
