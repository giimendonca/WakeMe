package com.wakeme.app.ui.screens.messages

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.wakeme.app.domain.model.AlertBackgroundColor
import com.wakeme.app.domain.model.AlertBackgroundSettings
import com.wakeme.app.domain.model.AlertBackgroundType
import com.wakeme.app.domain.model.AlertDraft
import com.wakeme.app.domain.model.AlertGraphicPattern
import com.wakeme.app.domain.model.AlertIconChoice
import com.wakeme.app.domain.model.AlertImageFit
import com.wakeme.app.domain.model.EmojiPatternSettings
import com.wakeme.app.domain.model.isPreviewable

private const val MAX_TITLE_LENGTH = 60
private const val MAX_MESSAGE_LENGTH = 240
private val patternEmojis = listOf("😡", "🚨", "⚠️", "💸", "❤️", "😂", "✨", "🔥")

@Composable
fun MessagesScreen() {
    val context = LocalContext.current
    var draft by rememberSaveable(stateSaver = alertDraftSaver) { mutableStateOf(AlertDraft()) }
    var imageAccessMessage by rememberSaveable { mutableStateOf<String?>(null) }
    val imagePicker = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            val previous = draft.background.imageUri
            if (previous != uri.toString()) releaseImageAccess(context, previous)
            val canPersist = try {
                context.contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
                true
            } catch (_: SecurityException) {
                false
            } catch (_: IllegalArgumentException) {
                false
            }
            imageAccessMessage = if (canPersist) null else "A imagem está disponível nesta sessão; escolha novamente se o acesso expirar."
            draft = draft.copy(background = draft.background.copy(type = AlertBackgroundType.DEVICE_IMAGE, imageUri = uri.toString()))
        }
    }

    BoxWithConstraints(Modifier.fillMaxSize()) {
        val wideLayout = maxWidth >= 900.dp
        if (wideLayout) {
            Row(
                Modifier.fillMaxSize().padding(horizontal = 28.dp, vertical = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(28.dp),
                verticalAlignment = Alignment.Top
            ) {
                EditorPanel(draft, { draft = it }, onClear = {
                    releaseImageAccess(context, draft.background.imageUri)
                    draft = AlertDraft()
                    imageAccessMessage = null
                }, onChooseImage = { imagePicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) }, onRemoveImage = {
                    releaseImageAccess(context, draft.background.imageUri)
                    draft = draft.copy(background = draft.background.copy(type = AlertBackgroundType.SOLID, imageUri = null))
                    imageAccessMessage = null
                }, imageAccessMessage, Modifier.weight(1f).fillMaxSize().verticalScroll(rememberScrollState()))
                Column(Modifier.weight(0.85f).verticalScroll(rememberScrollState()), horizontalAlignment = Alignment.CenterHorizontally) {
                    SectionTitle("Prévia no dispositivo")
                    if (!draft.isPreviewable()) EmptyPreview() else AlertPreview(draft)
                }
            }
        } else {
            Column(
                Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(horizontal = 20.dp, vertical = 18.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                EditorPanel(draft, { draft = it }, onClear = {
                    releaseImageAccess(context, draft.background.imageUri)
                    draft = AlertDraft()
                    imageAccessMessage = null
                }, onChooseImage = { imagePicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) }, onRemoveImage = {
                    releaseImageAccess(context, draft.background.imageUri)
                    draft = draft.copy(background = draft.background.copy(type = AlertBackgroundType.SOLID, imageUri = null))
                    imageAccessMessage = null
                }, imageAccessMessage, Modifier.fillMaxWidth())
                SectionTitle("Prévia no dispositivo")
                if (!draft.isPreviewable()) EmptyPreview() else AlertPreview(draft)
                Spacer(Modifier.height(12.dp))
            }
        }
    }
}

@Composable
private fun EditorPanel(
    draft: AlertDraft,
    onDraftChange: (AlertDraft) -> Unit,
    onClear: () -> Unit,
    onChooseImage: () -> Unit,
    onRemoveImage: () -> Unit,
    imageAccessMessage: String?,
    modifier: Modifier = Modifier
) {
    Column(modifier, verticalArrangement = Arrangement.spacedBy(14.dp)) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) {
                Text("Monte seu alerta", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                Text("Combine texto e elementos visuais do seu jeito.", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            TextButton(onClick = onClear, modifier = Modifier.testTag("reset-alert-composer")) { Text("Limpar") }
        }
        OutlinedTextField(
            value = draft.title,
            onValueChange = { onDraftChange(draft.copy(title = it.take(MAX_TITLE_LENGTH))) },
            modifier = Modifier.fillMaxWidth().testTag("alert-title-input"),
            label = { Text("Título (opcional)") },
            supportingText = { Text("${draft.title.length}/$MAX_TITLE_LENGTH") },
            singleLine = true
        )
        OutlinedTextField(
            value = draft.message,
            onValueChange = { onDraftChange(draft.copy(message = it.take(MAX_MESSAGE_LENGTH))) },
            modifier = Modifier.fillMaxWidth().testTag("alert-message-input"),
            label = { Text("Mensagem") },
            placeholder = { Text("Escreva o que deseja comunicar") },
            supportingText = { Text(if (draft.message.isBlank()) "A mensagem é necessária para mostrar a prévia." else "${draft.message.length}/$MAX_MESSAGE_LENGTH") },
            isError = draft.message.isNotBlank() && draft.message.length > MAX_MESSAGE_LENGTH,
            minLines = 2,
            maxLines = 4
        )
        SectionTitle("Ícone")
        Text("Escolha um símbolo ou deixe sem ícone.", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        IconChoices(draft.icon) { onDraftChange(draft.copy(icon = it)) }
        SectionTitle("Fundo")
        ChoiceChips(AlertBackgroundType.entries.map { it to it.label() }, draft.background.type, "background-type") {
            onDraftChange(draft.copy(background = draft.background.copy(type = it)))
        }
        if (draft.background.type != AlertBackgroundType.DEVICE_IMAGE) {
            Text("Cor de fundo", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
            ColorChoices(draft.background.color) { onDraftChange(draft.copy(background = draft.background.copy(color = it))) }
        }
        when (draft.background.type) {
            AlertBackgroundType.SOLID -> Unit
            AlertBackgroundType.EMOJI_PATTERN -> EmojiPatternEditor(draft.background.emojiPattern) { settings ->
                onDraftChange(draft.copy(background = draft.background.copy(emojiPattern = settings)))
            }
            AlertBackgroundType.GRAPHIC_PATTERN -> {
                Text("Padrão gráfico", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
                ChoiceChips(AlertGraphicPattern.entries.map { it to it.label() }, draft.background.graphicPattern, "graphic-pattern") {
                    onDraftChange(draft.copy(background = draft.background.copy(graphicPattern = it)))
                }
            }
            AlertBackgroundType.DEVICE_IMAGE -> ImageEditor(draft.background.imageUri, draft.background.imageFit, imageAccessMessage, onChooseImage, onRemoveImage) { fit ->
                onDraftChange(draft.copy(background = draft.background.copy(imageFit = fit)))
            }
        }
    }
}

@Composable
private fun IconChoices(selected: AlertIconChoice, onSelect: (AlertIconChoice) -> Unit) {
    val icons = listOf(AlertIconChoice.NONE, AlertIconChoice.NOTIFICATION, AlertIconChoice.WARNING, AlertIconChoice.HEART, AlertIconChoice.STAR,
        AlertIconChoice.EMOJI_ANGRY, AlertIconChoice.EMOJI_SIREN, AlertIconChoice.EMOJI_WARNING, AlertIconChoice.EMOJI_MONEY,
        AlertIconChoice.EMOJI_HEART, AlertIconChoice.EMOJI_LAUGH, AlertIconChoice.EMOJI_SPARKLES)
    icons.chunked(4).forEach { row ->
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            row.forEach { choice ->
                FilterChip(
                    selected == choice,
                    onClick = { onSelect(choice) },
                    label = { Text(choice.emoji ?: choice.label(), maxLines = 1) },
                    modifier = Modifier.weight(1f).testTag("alert-icon-${choice.storageKey}"),
                    leadingIcon = if (selected == choice) ({ Icon(Icons.Filled.Check, contentDescription = null) }) else null
                )
            }
            repeat(4 - row.size) { Spacer(Modifier.weight(1f)) }
        }
    }
}

@Composable
private fun EmojiPatternEditor(settings: EmojiPatternSettings, onChange: (EmojiPatternSettings) -> Unit) {
    Text("Emoji do padrão", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
    Row(Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
        patternEmojis.forEach { emoji ->
            FilterChip(settings.emoji == emoji, { onChange(settings.copy(emoji = emoji)) }, label = { Text(emoji) }, modifier = Modifier.testTag("pattern-emoji-$emoji"))
        }
    }
    Text("Tamanho: ${settings.sizeDp.toInt()} dp", style = MaterialTheme.typography.bodyMedium)
    Slider(settings.sizeDp, { onChange(settings.copy(sizeDp = it)) }, valueRange = 18f..54f, modifier = Modifier.testTag("pattern-size-slider"))
    Text("Espaçamento: ${settings.spacingDp.toInt()} dp", style = MaterialTheme.typography.bodyMedium)
    Slider(settings.spacingDp, { onChange(settings.copy(spacingDp = it)) }, valueRange = 4f..48f, modifier = Modifier.testTag("pattern-spacing-slider"))
}

@Composable
private fun ImageEditor(uri: String?, fit: AlertImageFit, message: String?, onChoose: () -> Unit, onRemove: () -> Unit, onFit: (AlertImageFit) -> Unit) {
    if (uri == null) {
        Text("Escolha uma imagem do dispositivo para usar como fundo.", color = MaterialTheme.colorScheme.onSurfaceVariant)
        Button(onClick = onChoose, modifier = Modifier.fillMaxWidth().testTag("choose-alert-image")) { Text("Escolher imagem") }
    } else {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            OutlinedButton(onClick = onChoose, modifier = Modifier.weight(1f)) { Text("Trocar imagem") }
            TextButton(onClick = onRemove, modifier = Modifier.testTag("remove-alert-image")) { Text("Remover") }
        }
        Text("Apresentação da imagem", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
        ChoiceChips(AlertImageFit.entries.map { it to if (it == AlertImageFit.FILL) "Preencher" else "Ajustar" }, fit, "image-fit", onFit)
        if (message != null) Text(message, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
    }
}

@Composable
private fun ColorChoices(selected: AlertBackgroundColor, onSelect: (AlertBackgroundColor) -> Unit) {
    Row(Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        AlertBackgroundColor.entries.forEach { option ->
            val color = Color(option.storageValue.toInt())
            Surface(
                onClick = { onSelect(option) },
                modifier = Modifier.size(42.dp).clip(CircleShape).testTag("background-color-${option.name.lowercase()}"),
                shape = CircleShape,
                color = color,
                border = BorderStroke(if (selected == option) 3.dp else 1.dp, if (selected == option) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline)
            ) {
                Box(contentAlignment = Alignment.Center) { if (selected == option) Icon(Icons.Filled.Check, "Selecionada", tint = if (option == AlertBackgroundColor.PAPER || option == AlertBackgroundColor.LILAC) Color.Black else Color.White) }
            }
        }
    }
}

@Composable
private fun <T> ChoiceChips(options: List<Pair<T, String>>, selected: T, tag: String, onSelect: (T) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
        options.chunked(3).forEach { row ->
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                row.forEach { (value, label) ->
                    FilterChip(selected == value, { onSelect(value) }, label = { Text(label) }, modifier = Modifier.weight(1f).testTag("$tag-${value.toString().lowercase()}"))
                }
                repeat(3 - row.size) { Spacer(Modifier.weight(1f)) }
            }
        }
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(text, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
}

@Composable
private fun EmptyPreview() {
    Card(Modifier.fillMaxWidth().testTag("empty-alert-preview"), shape = RoundedCornerShape(20.dp)) {
        Text("A prévia será exibida aqui quando houver uma mensagem.", Modifier.fillMaxWidth().padding(24.dp), color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

private fun AlertBackgroundType.label() = when (this) {
    AlertBackgroundType.SOLID -> "Cor sólida"
    AlertBackgroundType.EMOJI_PATTERN -> "Emojis"
    AlertBackgroundType.GRAPHIC_PATTERN -> "Padrões"
    AlertBackgroundType.DEVICE_IMAGE -> "Galeria"
}

private fun AlertGraphicPattern.label() = when (this) {
    AlertGraphicPattern.GEOMETRIC -> "Geométrico"
    AlertGraphicPattern.ALERT_SYMBOLS -> "Alerta"
    AlertGraphicPattern.ABSTRACT -> "Abstrato"
    AlertGraphicPattern.PLAYFUL -> "Divertido"
}

private fun AlertIconChoice.label() = when (this) {
    AlertIconChoice.NONE -> "Nenhum"
    AlertIconChoice.NOTIFICATION -> "Aviso"
    AlertIconChoice.WARNING -> "Atenção"
    AlertIconChoice.HEART -> "Coração"
    AlertIconChoice.STAR -> "Estrela"
    else -> emoji.orEmpty()
}

private fun releaseImageAccess(context: android.content.Context, rawUri: String?) {
    if (rawUri == null) return
    try {
        context.contentResolver.releasePersistableUriPermission(Uri.parse(rawUri), Intent.FLAG_GRANT_READ_URI_PERMISSION)
    } catch (_: SecurityException) {
        // The selected photo provider may grant temporary access only.
    } catch (_: IllegalArgumentException) {
        // No persistable grant was available for this URI.
    }
}

private val alertDraftSaver: Saver<AlertDraft, Any> = mapSaver(
    save = { draft ->
        mapOf(
            "title" to draft.title,
            "message" to draft.message,
            "icon" to draft.icon.storageKey,
            "background" to draft.background.type.name,
            "color" to draft.background.color.storageValue,
            "emoji" to draft.background.emojiPattern.emoji,
            "emojiSize" to draft.background.emojiPattern.sizeDp,
            "emojiSpacing" to draft.background.emojiPattern.spacingDp,
            "graphic" to draft.background.graphicPattern.name,
            "uri" to (draft.background.imageUri ?: ""),
            "fit" to draft.background.imageFit.name
        )
    },
    restore = { values ->
        val backgroundType = runCatching { AlertBackgroundType.valueOf(values["background"] as String) }.getOrDefault(AlertBackgroundType.SOLID)
        val graphic = runCatching { AlertGraphicPattern.valueOf(values["graphic"] as String) }.getOrDefault(AlertGraphicPattern.GEOMETRIC)
        val fit = runCatching { AlertImageFit.valueOf(values["fit"] as String) }.getOrDefault(AlertImageFit.FILL)
        AlertDraft(
            title = values["title"] as? String ?: "",
            message = values["message"] as? String ?: "",
            icon = AlertIconChoice.fromStorageKey(values["icon"] as? String),
            background = AlertBackgroundSettings(
                type = backgroundType,
                color = AlertBackgroundColor.fromStorageValue(values["color"] as? Long ?: AlertBackgroundColor.MIDNIGHT.storageValue),
                emojiPattern = EmojiPatternSettings(values["emoji"] as? String ?: "🚨", values["emojiSize"] as? Float ?: 34f, values["emojiSpacing"] as? Float ?: 20f),
                graphicPattern = graphic,
                imageUri = (values["uri"] as? String)?.ifBlank { null },
                imageFit = fit
            )
        )
    }
)
