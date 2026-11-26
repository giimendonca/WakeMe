package com.wakeme.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.wakeme.app.ui.theme.WakeMePalette

@Composable
fun GradientCard(darkTheme: Boolean, content: @Composable BoxScope.() -> Unit) {
    val colors = if (darkTheme) WakeMePalette.DarkGradient else WakeMePalette.LightGradient
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(28.dp))
            .background(Brush.linearGradient(colors.map { it.copy(alpha = 0.88f) })),
        content = content
    )
}
