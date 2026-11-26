package com.wakeme.app.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.wakeme.app.data.preferences.ThemePreference
import com.wakeme.app.ui.components.GradientCard
import com.wakeme.app.ui.components.SectionTitle

@Composable
fun HomeScreen(
    themePreference: ThemePreference,
    onThemePreferenceChange: (ThemePreference) -> Unit
) {
    val darkTheme = when (themePreference) {
        ThemePreference.SYSTEM -> isSystemInDarkTheme()
        ThemePreference.LIGHT -> false
        ThemePreference.DARK -> true
    }
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val contentWidth = if (maxWidth > 700.dp) 680.dp else maxWidth
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 24.dp)
                .width(contentWidth)
                .align(Alignment.TopCenter),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text("WakeMe", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
            Text("Bom ter você por aqui.", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.72f))

            GradientCard(darkTheme = darkTheme) {
                Column(Modifier.padding(24.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("Um jeito carinhoso de se conectar", style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.onPrimary, fontWeight = FontWeight.SemiBold)
                    Text("Fique perto de quem importa, mesmo à distância.", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onPrimary)
                }
            }

            SectionTitle("Suas conexões")
            InfoCard("Conexões estarão aqui", "Quando esse recurso estiver disponível, você poderá acompanhar suas conexões autorizadas.")

            SectionTitle("Enviar um alerta")
            InfoCard("Envio ainda indisponível", "O envio de alertas será habilitado em uma etapa futura. Nenhum alerta pode ser enviado nesta versão.", highlighted = true)

            SectionTitle("Personalização")
            Card(shape = RoundedCornerShape(22.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                Column(Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("Tema do aplicativo", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        ThemePreference.entries.forEach { option ->
                            FilterChip(
                                selected = themePreference == option,
                                onClick = { onThemePreferenceChange(option) },
                                label = { Text(option.label()) }
                            )
                        }
                    }
                    Text("Wallpapers e outras opções visuais estarão disponíveis futuramente.", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
                }
            }
            Spacer(Modifier.height(8.dp))
        }
    }
}

@Composable
private fun InfoCard(title: String, description: String, highlighted: Boolean = false) {
    Card(
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (highlighted) MaterialTheme.colorScheme.primary.copy(alpha = 0.12f) else MaterialTheme.colorScheme.surface
        )
    ) {
        Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            Text(description, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.72f))
        }
    }
}

private fun ThemePreference.label(): String = when (this) {
    ThemePreference.SYSTEM -> "Sistema"
    ThemePreference.LIGHT -> "Claro"
    ThemePreference.DARK -> "Escuro"
}
