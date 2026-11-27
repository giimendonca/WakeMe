package com.wakeme.app.ui.screens.connections

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Groups
import androidx.compose.runtime.Composable
import com.wakeme.app.ui.components.EmptyStateScreen

@Composable
fun ConnectionsScreen() {
    EmptyStateScreen(
        icon = Icons.Filled.Groups,
        title = "Suas conexões começam aqui",
        description = "Quando conexões estiverem disponíveis, as pessoas autorizadas aparecerão nesta área."
    )
}
