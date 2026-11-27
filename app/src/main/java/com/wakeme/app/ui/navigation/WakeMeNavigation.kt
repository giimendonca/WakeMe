package com.wakeme.app.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.wakeme.app.data.preferences.AccentColor
import com.wakeme.app.data.preferences.ThemePreference
import com.wakeme.app.ui.screens.connections.ConnectionsScreen
import com.wakeme.app.ui.screens.history.HistoryScreen
import com.wakeme.app.ui.screens.home.HomeScreen
import com.wakeme.app.ui.screens.messages.MessagesScreen
import com.wakeme.app.ui.screens.personalization.PersonalizationScreen
import com.wakeme.app.ui.screens.settings.SettingsScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WakeMeNavigation(
    themePreference: ThemePreference,
    accentColor: AccentColor,
    onThemePreferenceChange: (ThemePreference) -> Unit,
    onAccentColorChange: (AccentColor) -> Unit,
    onSignOut: () -> Unit
) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = WakeMeDestination.fromRoute(backStackEntry?.destination?.route)
    val isSettings = currentDestination == WakeMeDestination.SETTINGS

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(if (currentDestination == WakeMeDestination.HOME) "WakeMe" else currentDestination.label) },
                navigationIcon = {
                    if (isSettings) {
                        IconButton(
                            onClick = {
                                if (!navController.popBackStack()) {
                                    navController.navigate(WakeMeDestination.HOME.route) {
                                        popUpTo(WakeMeDestination.HOME.route) { inclusive = true }
                                    }
                                }
                            },
                            modifier = Modifier.testTag("back-from-settings")
                        ) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                        }
                    }
                },
                actions = {
                    if (!isSettings) {
                        IconButton(
                            onClick = {
                                navController.navigate(WakeMeDestination.SETTINGS.route) {
                                    launchSingleTop = true
                                }
                            },
                            modifier = Modifier.testTag("open-settings")
                        ) {
                            Icon(Icons.Filled.Settings, contentDescription = "Configurações")
                        }
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        bottomBar = {
            if (!isSettings) {
                NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
                    WakeMeDestination.mainDestinations.forEach { destination ->
                        NavigationBarItem(
                            selected = currentDestination == destination,
                            onClick = {
                                navController.navigate(destination.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = {
                                Icon(destination.icon, contentDescription = null)
                            },
                            label = {
                                Text(destination.label, maxLines = 1, softWrap = false, overflow = TextOverflow.Clip)
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.primary,
                                selectedTextColor = MaterialTheme.colorScheme.primary,
                                indicatorColor = MaterialTheme.colorScheme.primaryContainer
                            ),
                            alwaysShowLabel = true,
                            modifier = Modifier.testTag("nav-${destination.route}")
                        )
                    }
                }
            }
        }
    ) { contentPadding ->
        NavHost(
            navController = navController,
            startDestination = WakeMeDestination.HOME.route,
            modifier = Modifier.padding(contentPadding)
        ) {
            composable(WakeMeDestination.HOME.route) {
                HomeScreen(themePreference, onThemePreferenceChange)
            }
            composable(WakeMeDestination.CONNECTIONS.route) { ConnectionsScreen() }
            composable(WakeMeDestination.MESSAGES.route) { MessagesScreen() }
            composable(WakeMeDestination.HISTORY.route) { HistoryScreen() }
            composable(WakeMeDestination.PERSONALIZATION.route) {
                PersonalizationScreen(
                    themePreference = themePreference,
                    accentColor = accentColor,
                    onThemePreferenceChange = onThemePreferenceChange,
                    onAccentColorChange = onAccentColorChange
                )
            }
            composable(WakeMeDestination.SETTINGS.route) {
                SettingsScreen(themePreference, onThemePreferenceChange, onSignOut)
            }
        }
    }
}
