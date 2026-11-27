package com.wakeme.app.ui.screens.messages

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.wakeme.app.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AlertEditorTest {
    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun editorComposesContentAndBackgroundThenClearRestoresEmptyDraft() {
        composeRule.onNodeWithTag("nav-messages").performClick()
        composeRule.onNodeWithTag("empty-alert-preview").assertIsDisplayed()

        composeRule.onNodeWithTag("alert-title-input").performTextInput("Cheguei")
        composeRule.onNodeWithTag("alert-message-input").performTextInput("Estou na porta")
        composeRule.onNodeWithTag("full-alert-preview").assertIsDisplayed()
        composeRule.onNodeWithText("Cheguei").assertIsDisplayed()
        composeRule.onNodeWithText("Estou na porta").assertIsDisplayed()

        composeRule.onNodeWithTag("alert-icon-emoji_heart").performClick()
        composeRule.onNodeWithTag("background-type-emoji_pattern").performClick()
        composeRule.onNodeWithTag("pattern-emoji-❤️").performClick()
        composeRule.onNodeWithTag("background-color-teal").performClick()
        composeRule.onNodeWithTag("pattern-size-slider").assertIsDisplayed()
        composeRule.onNodeWithTag("pattern-spacing-slider").assertIsDisplayed()
        composeRule.onNodeWithTag("full-alert-preview").assertIsDisplayed()

        composeRule.onNodeWithTag("reset-alert-composer").performClick()
        composeRule.onNodeWithTag("alert-title-input").assertTextEquals("")
        composeRule.onNodeWithTag("alert-message-input").assertTextEquals("")
        composeRule.onNodeWithTag("empty-alert-preview").assertIsDisplayed()
        composeRule.onAllNodesWithTag("full-alert-preview").assertCountEquals(0)
    }

    @Test
    fun graphicPatternsAndPhotoPickerOptionsAreAvailable() {
        composeRule.onNodeWithTag("nav-messages").performClick()
        composeRule.onNodeWithTag("background-type-graphic_pattern").performClick()
        composeRule.onNodeWithTag("graphic-pattern-alert_symbols").assertIsDisplayed()
        composeRule.onNodeWithTag("background-type-device_image").performClick()
        composeRule.onNodeWithTag("choose-alert-image").assertIsDisplayed()
        composeRule.onNodeWithText("Escolher imagem").assertIsDisplayed()
    }
}
