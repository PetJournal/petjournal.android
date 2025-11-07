package com.soujunior.petjournal.login

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.soujunior.petjournal.ui.screens_app.account_manager.loginScreen.components.AccountConfirmationDialog
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginAccountConfirmationDialogTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun whenAccountNotConfirmed_shouldShowConfirmationDialog() {
        composeTestRule.setContent {
            val showDialog = remember { mutableStateOf(true) }

            if (showDialog.value) {
                AccountConfirmationDialog(
                    onDismiss = { showDialog.value = false }
                )
            }
        }

        composeTestRule.onNodeWithText("Confirmação do E-mail").assertExists()

        composeTestRule.onNodeWithText("Entendi").performClick()

        composeTestRule.waitUntil(timeoutMillis = 5_000) {
            composeTestRule.onAllNodesWithText("Confirmação do E-mail").fetchSemanticsNodes()
                .isEmpty()
        }

        composeTestRule.onNodeWithText("Confirmação do E-mail").assertDoesNotExist()
    }
}
