package com.wakeme.app.ui.navigation

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.wakeme.app.MainActivity
import com.wakeme.app.data.preferences.AccentColor
import com.wakeme.app.data.preferences.ThemePreference
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WakeMeNavigationTest {
    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun bottomBarOpensAllPrimaryDestinationsAndSettings() {
        composeRule.onNodeWithTag("nav-connections").performClick()
        composeRule.onNodeWithText("Suas conexões começam aqui").assertIsDisplayed()
        composeRule.onNodeWithTag("nav-connections").assertIsSelected()

        composeRule.onNodeWithTag("nav-messages").performClick()
        composeRule.onNodeWithText("Suas mensagens aparecerão aqui").assertIsDisplayed()

        composeRule.onNodeWithTag("nav-history").performClick()
        composeRule.onNodeWithText("Seu histórico ficará aqui").assertIsDisplayed()

        composeRule.onNodeWithTag("nav-personalization").performClick()
        composeRule.onNodeWithText("Deixe o WakeMe com a sua cara").assertIsDisplayed()

        composeRule.onNodeWithTag("nav-home").performClick()
        composeRule.onNodeWithText("Bom ter você por aqui.").assertIsDisplayed()

        composeRule.onNodeWithTag("open-settings").performClick()
        composeRule.onNodeWithText("A preferência fica salva neste dispositivo e pode acompanhar o tema do Android.")
            .assertIsDisplayed()
        composeRule.onNodeWithText("Escuro").performClick()
        composeRule.onNodeWithText("Escuro").assertIsSelected()

        composeRule.onNodeWithTag("back-from-settings").performClick()
        composeRule.onNodeWithTag("nav-personalization").performClick()
        composeRule.onNodeWithText("Escuro").assertIsSelected()
        composeRule.onNodeWithText("Sistema").performClick()
    }

    @Test
    fun accentAndThemeChoicesUpdatePreviewAndPersistAcrossRecreation() {
        composeRule.onNodeWithTag("nav-personalization").performClick()
        composeRule.onNodeWithTag("appearance-preview-button").assertIsDisplayed()

        AccentColor.entries.forEach { accent ->
            composeRule.onNodeWithTag("accent-${accent.name.lowercase()}").performClick()
            composeRule.onNodeWithTag("accent-${accent.name.lowercase()}").assertIsSelected()

            ThemePreference.entries.forEach { theme ->
                composeRule.onNodeWithTag("theme-${theme.name.lowercase()}").performClick()
                composeRule.onNodeWithTag("theme-${theme.name.lowercase()}").assertIsSelected()
                composeRule.onNodeWithTag("appearance-preview").assertIsDisplayed()
            }
        }

        composeRule.activityRule.scenario.recreate()
        composeRule.onNodeWithTag("accent-green").assertIsSelected()
        composeRule.onNodeWithTag("theme-dark").assertIsSelected()

        composeRule.onNodeWithTag("accent-purple").performClick()
        composeRule.onNodeWithTag("theme-system").performClick()
    }
}
