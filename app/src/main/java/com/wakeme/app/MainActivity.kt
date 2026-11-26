package com.wakeme.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.wakeme.app.data.preferences.ThemePreference
import com.wakeme.app.data.preferences.ThemePreferencesRepository
import com.wakeme.app.ui.screens.home.HomeScreen
import com.wakeme.app.ui.theme.WakeMeTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val preferencesRepository by lazy { ThemePreferencesRepository(applicationContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val themePreference by preferencesRepository.themePreference.collectAsState(initial = ThemePreference.SYSTEM)
            val darkTheme = when (themePreference) {
                ThemePreference.SYSTEM -> androidx.compose.foundation.isSystemInDarkTheme()
                ThemePreference.LIGHT -> false
                ThemePreference.DARK -> true
            }
            WakeMeTheme(darkTheme = darkTheme) {
                Surface(modifier = Modifier.fillMaxSize()) {
                    HomeScreen(
                        themePreference = themePreference,
                        onThemePreferenceChange = { preference ->
                            lifecycleScope.launch { preferencesRepository.setThemePreference(preference) }
                        }
                    )
                }
            }
        }
    }
}
