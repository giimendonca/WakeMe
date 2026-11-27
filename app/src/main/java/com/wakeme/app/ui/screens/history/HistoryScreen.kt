package com.wakeme.app.ui.screens.history

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.runtime.Composable
import com.wakeme.app.ui.components.EmptyStateScreen

@Composable
fun HistoryScreen() {
    EmptyStateScreen(
        icon = Icons.Filled.History,
        title = "Seu histórico ficará aqui",
        description = "Quando o envio de alertas estiver disponível, você poderá consultar os eventos nesta tela."
    )
}
