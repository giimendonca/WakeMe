package com.wakeme.app.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

enum class WakeMeDestination(
    val route: String,
    val label: String,
    val icon: ImageVector
) {
    HOME("home", "Início", Icons.Filled.Home),
    CONNECTIONS("connections", "Conexões", Icons.Filled.Groups),
    MESSAGES("messages", "Alertas", Icons.Filled.ChatBubbleOutline),
    HISTORY("history", "Histórico", Icons.Filled.History),
    PERSONALIZATION("personalization", "Aparência", Icons.Filled.Palette),
    SETTINGS("settings", "Configurações", Icons.Filled.Settings);

    companion object {
        val mainDestinations = listOf(HOME, CONNECTIONS, MESSAGES, HISTORY, PERSONALIZATION)
        fun fromRoute(route: String?): WakeMeDestination =
            entries.firstOrNull { it.route == route } ?: HOME
    }
}
