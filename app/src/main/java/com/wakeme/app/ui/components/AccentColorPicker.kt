package com.wakeme.app.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.wakeme.app.data.preferences.AccentColor
import com.wakeme.app.ui.theme.WakeMePalette

@Composable
fun AccentColorPicker(
    selected: AccentColor,
    onSelected: (AccentColor) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        AccentColor.entries.toList().chunked(3).forEach { rowColors ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Top
            ) {
                rowColors.forEach { accentColor ->
                    val color = WakeMePalette.accent(accentColor)
                    Column(
                        modifier = Modifier
                            .width(84.dp)
                            .clip(MaterialTheme.shapes.medium)
                            .selectable(
                                selected = selected == accentColor,
                                role = Role.RadioButton,
                                onClick = { onSelected(accentColor) }
                            )
                            .testTag("accent-${accentColor.name.lowercase()}")
                            .padding(vertical = 4.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .then(
                                    if (selected == accentColor) {
                                        Modifier.border(3.dp, MaterialTheme.colorScheme.primary, CircleShape)
                                    } else {
                                        Modifier.border(1.dp, MaterialTheme.colorScheme.outlineVariant, CircleShape)
                                    }
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(if (selected == accentColor) 40.dp else 46.dp)
                                    .clip(CircleShape)
                                    .background(color),
                                contentAlignment = Alignment.Center
                            ) {
                                if (selected == accentColor) {
                                    Icon(
                                        imageVector = Icons.Filled.Check,
                                        contentDescription = null,
                                        tint = if (color.luminance() > 0.179f) Color.Black else Color.White,
                                        modifier = Modifier.size(22.dp)
                                    )
                                }
                            }
                        }
                        Text(
                            text = accentColor.label,
                            style = MaterialTheme.typography.labelMedium,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

private val AccentColor.label: String
    get() = when (this) {
        AccentColor.PURPLE -> "Roxo"
        AccentColor.PINK -> "Rosa"
        AccentColor.TURQUOISE -> "Turquesa"
        AccentColor.BLUE -> "Azul"
        AccentColor.GREEN -> "Verde"
    }
