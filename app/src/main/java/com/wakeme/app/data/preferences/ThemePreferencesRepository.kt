package com.wakeme.app.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.themeDataStore by preferencesDataStore(name = "user_preferences")

class ThemePreferencesRepository(private val context: Context) {
    val themePreference: Flow<ThemePreference> = context.themeDataStore.data.map { preferences ->
        ThemePreference.fromStoredValue(preferences[THEME_KEY])
    }

    suspend fun setThemePreference(themePreference: ThemePreference) {
        context.themeDataStore.edit { preferences ->
            preferences[THEME_KEY] = themePreference.name
        }
    }

    private companion object {
        val THEME_KEY = stringPreferencesKey("theme_preference")
    }
}
