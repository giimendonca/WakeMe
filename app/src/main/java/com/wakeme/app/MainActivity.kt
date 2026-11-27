package com.wakeme.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.material3.Surface
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.wakeme.app.data.auth.FirebaseAuthRepository
import com.wakeme.app.data.profile.FirestoreProfileRepository
import com.wakeme.app.domain.model.SessionState
import com.wakeme.app.ui.auth.AuthViewModel
import com.wakeme.app.ui.screens.auth.AuthScreen
import androidx.lifecycle.lifecycleScope
import com.wakeme.app.data.preferences.AppearancePreferences
import com.wakeme.app.data.preferences.ThemePreference
import com.wakeme.app.data.preferences.ThemePreferencesRepository
import com.wakeme.app.ui.navigation.WakeMeNavigation
import com.wakeme.app.ui.theme.WakeMeTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val preferencesRepository by lazy { ThemePreferencesRepository(applicationContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current
            val appearance by preferencesRepository.appearancePreferences.collectAsState(
                initial = AppearancePreferences()
            )
            val darkTheme = when (appearance.theme) {
                ThemePreference.SYSTEM -> androidx.compose.foundation.isSystemInDarkTheme()
                ThemePreference.LIGHT -> false
                ThemePreference.DARK -> true
            }
            WakeMeTheme(darkTheme = darkTheme, accentColor = appearance.accentColor) {
                Surface(modifier = Modifier.fillMaxSize()) {
                    if (FirebaseApp.getApps(context).isEmpty()) {
                        Box(Modifier.fillMaxSize().padding(24.dp), contentAlignment = Alignment.Center) {
                            Text("Firebase ainda não está configurado. Adicione google-services.json conforme docs/SETUP.md e reabra o app.", color = MaterialTheme.colorScheme.error)
                        }
                    } else {
                    val authViewModel: AuthViewModel = viewModel(factory = object : androidx.lifecycle.ViewModelProvider.Factory {
                        @Suppress("UNCHECKED_CAST")
                        override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T =
                            AuthViewModel(FirebaseAuthRepository(FirebaseAuth.getInstance()),
                                FirestoreProfileRepository(FirebaseFirestore.getInstance())) as T
                    })
                    val authState by authViewModel.state.collectAsState()
                    when (val session = authState.session) {
                        SessionState.Loading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { CircularProgressIndicator() }
                        SessionState.SignedOut -> AuthScreen(authState.busy, authState.message, authState.messageIsError,
                            authViewModel::signIn, authViewModel::register, authViewModel::resetPassword)
                        is SessionState.SignedIn -> WakeMeNavigation(
                        themePreference = appearance.theme,
                        accentColor = appearance.accentColor,
                        onThemePreferenceChange = { preference ->
                            lifecycleScope.launch { preferencesRepository.setThemePreference(preference) }
                        },
                        onAccentColorChange = { accentColor ->
                            lifecycleScope.launch { preferencesRepository.setAccentColor(accentColor) }
                        }, onSignOut = authViewModel::signOut)
                    }
                    }
                }
            }
        }
    }
}
