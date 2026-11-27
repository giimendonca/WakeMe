package com.wakeme.app.domain.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class AlertDraftTest {
    @Test
    fun draftKeepsContentAndVisualConfigurationIndependent() {
        val draft = AlertDraft(
            title = "Lembrete",
            message = "Estou chegando",
            icon = AlertIconChoice.NONE,
            background = AlertBackgroundSettings(
                type = AlertBackgroundType.EMOJI_PATTERN,
                color = AlertBackgroundColor.TEAL,
                emojiPattern = EmojiPatternSettings("❤️", 46f, 12f)
            )
        )

        assertEquals("Lembrete", draft.title)
        assertEquals("Estou chegando", draft.message)
        assertEquals(AlertIconChoice.NONE, draft.icon)
        assertEquals(AlertBackgroundType.EMOJI_PATTERN, draft.background.type)
        assertEquals(AlertBackgroundColor.TEAL, draft.background.color)
        assertEquals(EmojiPatternSettings("❤️", 46f, 12f), draft.background.emojiPattern)
    }

    @Test
    fun backgroundTypesAndImagePresentationCanBeChangedIndependently() {
        val solid = AlertBackgroundSettings()
        val graphic = solid.copy(type = AlertBackgroundType.GRAPHIC_PATTERN, graphicPattern = AlertGraphicPattern.ABSTRACT)
        val image = solid.copy(type = AlertBackgroundType.DEVICE_IMAGE, imageUri = "content://photo/1", imageFit = AlertImageFit.FIT)

        assertNotEquals(solid.type, graphic.type)
        assertEquals(AlertGraphicPattern.ABSTRACT, graphic.graphicPattern)
        assertEquals("content://photo/1", image.imageUri)
        assertEquals(AlertImageFit.FIT, image.imageFit)
        assertNull(AlertDraft().background.imageUri)
    }

    @Test
    fun previewRequiresMessageAndRespectsContentLimits() {
        assertFalse(AlertDraft().isPreviewable())
        assertFalse(AlertDraft(message = "  ").isPreviewable())
        assertTrue(AlertDraft(message = "Olá").isPreviewable())
        assertFalse(AlertDraft(message = "x".repeat(MAX_ALERT_MESSAGE_LENGTH + 1)).isPreviewable())
        assertFalse(AlertDraft(title = "x".repeat(MAX_ALERT_TITLE_LENGTH + 1), message = "Olá").isPreviewable())
    }

    @Test
    fun clearingDraftRestoresEmptyContentAndDefaultVisualSettings() {
        val created = AlertDraft("Oi", "Mensagem", AlertIconChoice.EMOJI_HEART)
        val cleared = created.copy(title = "", message = "", icon = AlertIconChoice.NONE, background = AlertBackgroundSettings())

        assertEquals(AlertDraft(), cleared)
        assertFalse(cleared.isPreviewable())
    }

    @Test
    fun persistedEnumValuesFallBackSafelyForUnknownEntries() {
        assertEquals(AlertIconChoice.NONE, AlertIconChoice.fromStorageKey("unsupported"))
        assertEquals(AlertBackgroundColor.MIDNIGHT, AlertBackgroundColor.fromStorageValue(-1L))
    }
}
