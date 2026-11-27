package com.wakeme.app.domain.model

enum class AlertIconChoice(
    val storageKey: String,
    val emoji: String? = null
) {
    NONE("none"),
    NOTIFICATION("notification"),
    WARNING("warning"),
    HEART("heart"),
    STAR("star"),
    EMOJI_ANGRY("emoji_angry", "😡"),
    EMOJI_SIREN("emoji_siren", "🚨"),
    EMOJI_WARNING("emoji_warning", "⚠️"),
    EMOJI_MONEY("emoji_money", "💸"),
    EMOJI_HEART("emoji_heart", "❤️"),
    EMOJI_LAUGH("emoji_laugh", "😂"),
    EMOJI_SPARKLES("emoji_sparkles", "✨");

    companion object {
        fun fromStorageKey(key: String?): AlertIconChoice =
            entries.firstOrNull { it.storageKey == key } ?: NONE
    }
}

enum class AlertBackgroundType {
    SOLID,
    EMOJI_PATTERN,
    GRAPHIC_PATTERN,
    DEVICE_IMAGE
}

enum class AlertGraphicPattern {
    GEOMETRIC,
    ALERT_SYMBOLS,
    ABSTRACT,
    PLAYFUL
}

enum class AlertImageFit {
    FILL,
    FIT
}

enum class AlertBackgroundColor(
    val storageValue: Long,
    val label: String
) {
    MIDNIGHT(0xFF171B2C, "Noite"),
    PLUM(0xFF332158, "Ameixa"),
    BLUE(0xFF153C62, "Azul"),
    TEAL(0xFF164A44, "Turquesa"),
    ROSE(0xFF692C4C, "Rosa"),
    SLATE(0xFF263449, "Ardósia"),
    PAPER(0xFFF4F1E8, "Papel"),
    LILAC(0xFFE7DAF3, "Lilás");

    companion object {
        fun fromStorageValue(value: Long): AlertBackgroundColor =
            entries.firstOrNull { it.storageValue == value } ?: MIDNIGHT
    }
}

data class EmojiPatternSettings(
    val emoji: String = "🚨",
    val sizeDp: Float = 34f,
    val spacingDp: Float = 20f
)

data class AlertBackgroundSettings(
    val type: AlertBackgroundType = AlertBackgroundType.SOLID,
    val color: AlertBackgroundColor = AlertBackgroundColor.MIDNIGHT,
    val emojiPattern: EmojiPatternSettings = EmojiPatternSettings(),
    val graphicPattern: AlertGraphicPattern = AlertGraphicPattern.GEOMETRIC,
    val imageUri: String? = null,
    val imageFit: AlertImageFit = AlertImageFit.FILL
)

data class AlertDraft(
    val title: String = "",
    val message: String = "",
    val icon: AlertIconChoice = AlertIconChoice.NONE,
    val background: AlertBackgroundSettings = AlertBackgroundSettings()
)

const val MAX_ALERT_TITLE_LENGTH = 60
const val MAX_ALERT_MESSAGE_LENGTH = 240

fun AlertDraft.isPreviewable(): Boolean = message.isNotBlank() &&
    title.length <= MAX_ALERT_TITLE_LENGTH && message.length <= MAX_ALERT_MESSAGE_LENGTH
