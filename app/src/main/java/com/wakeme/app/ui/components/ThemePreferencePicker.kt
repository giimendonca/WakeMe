package com.wakeme.app.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.wakeme.app.data.preferences.ThemePreference

@Composable
fun ThemePreferencePicker(
    selected: ThemePreference,
    onSelected: (ThemePreference) -> Unit
) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        ThemePreference.entries.forEach { preference ->
            FilterChip(
                selected = selected == preference,
                onClick = { onSelected(preference) },
                label = { Text(preference.label) },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                    selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
                    selectedLeadingIconColor = MaterialTheme.colorScheme.onPrimary,
                    selectedTrailingIconColor = MaterialTheme.colorScheme.onPrimary
                ),
                modifier = Modifier.testTag("theme-${preference.name.lowercase()}")
            )
        }
    }
}

private val ThemePreference.label: String
    get() = when (this) {
        ThemePreference.SYSTEM -> "Sistema"
        ThemePreference.LIGHT -> "Claro"
        ThemePreference.DARK -> "Escuro"
    }
