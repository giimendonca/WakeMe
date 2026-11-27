package com.wakeme.app.data.preferences

enum class AccentColor {
    PURPLE,
    PINK,
    TURQUOISE,
    BLUE,
    GREEN;

    companion object {
        fun fromStoredValue(value: String?): AccentColor =
            entries.firstOrNull { it.name == value } ?: PURPLE
    }
}
