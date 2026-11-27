package com.wakeme.app.ui.screens.messages

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wakeme.app.domain.model.AlertDraft
import com.wakeme.app.domain.model.AlertIconChoice

@Composable
fun AlertPreview(draft: AlertDraft, modifier: Modifier = Modifier) {
    val foreground = Color.White

    BoxWithConstraints(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.TopCenter) {
        Box(
            modifier = Modifier
                .widthIn(max = 380.dp)
                .fillMaxWidth()
                .aspectRatio(9f / 16f)
                .clip(RoundedCornerShape(30.dp))
                .border(5.dp, Color(0xFF292833), RoundedCornerShape(30.dp))
                .testTag("full-alert-preview")
        ) {
            AlertBackgroundLayer(draft.background, Modifier.fillMaxSize())
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Black.copy(alpha = 0.24f), Color.Transparent, Color.Black.copy(alpha = 0.24f))
                        )
                    )
            )
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                AlertIcon(draft.icon, foreground)
                Surface(
                    shape = RoundedCornerShape(22.dp),
                    color = Color.Black.copy(alpha = 0.58f)
                ) {
                    Text(
                        text = draft.title.trim().ifBlank { "Seu alerta" },
                        modifier = Modifier.padding(horizontal = 18.dp, vertical = 12.dp).testTag("alert-preview-title"),
                        style = MaterialTheme.typography.headlineSmall,
                        color = foreground,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }
                Surface(
                    shape = RoundedCornerShape(22.dp),
                    color = Color.Black.copy(alpha = 0.58f)
                ) {
                    Text(
                        text = draft.message.trim(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 18.dp, vertical = 16.dp)
                            .testTag("alert-preview-message"),
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }
            }
            Text(
                "PRÉVIA · NÃO ENVIADO",
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 20.dp),
                style = MaterialTheme.typography.labelSmall,
                color = foreground.copy(alpha = 0.85f),
                letterSpacing = 1.sp
            )
        }
    }
}

@Composable
private fun AlertIcon(choice: AlertIconChoice, tint: Color) {
    if (choice.emoji != null) {
        Text(choice.emoji, fontSize = 48.sp, modifier = Modifier.testTag("alert-preview-icon"))
        return
    }
    val imageVector = when (choice) {
        AlertIconChoice.NOTIFICATION -> Icons.Filled.Notifications
        AlertIconChoice.WARNING -> Icons.Filled.WarningAmber
        AlertIconChoice.HEART -> Icons.Filled.Favorite
        AlertIconChoice.STAR -> Icons.Filled.Star
        else -> null
    }
    if (imageVector != null) {
        Icon(
            imageVector = imageVector,
            contentDescription = choice.name,
            modifier = Modifier.testTag("alert-preview-icon"),
            tint = tint
        )
    }
}
