package com.soujunior.petjournal.nameGenderScreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.printToLog
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.soujunior.petjournal.ui.screens_app.pets_screens.petNameAndGenderScreen.components.GenderSelector
import com.soujunior.petjournal.ui.components.Button3
import com.soujunior.petjournal.ui.components.DashedInputText
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NameGenderInstrumentedTest {


    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun textInput_should_return_error(){
        val name = "a"
        val nameError = listOf("Adicione um nome entre 2 a 30 caracteres")
        composeTestRule.setContent {
            DashedInputText(
                textValue = name,
                textError = nameError,
                onEvent = {},
                isError = true
            )
        }
        composeTestRule.onNodeWithText("Adicione um nome entre 2 a 30 caracteres").assertExists()
    }

    @Test
    fun textInput_should_accept_text(){
        val name = "B0linha"

        composeTestRule.setContent {
            DashedInputText(
                textValue = name,
                onEvent = {},
                isError = false,
            )
        }
        composeTestRule.onNodeWithTag("dashedInputField_test").performTextInput(name)
    }
    @Test
    fun testButtonWithCustomTag() {
        composeTestRule.setContent {
            Button3(
                submit = { /*TODO*/ },
                enableButton = false)
        }


        // Tenta encontrar o botão com a tag "button_test"
        composeTestRule.onNodeWithTag("button3_test").assertExists()
    }

    @Test
    fun genderButtons_should_not_be_empty(){
        val error = listOf("*Campo Obrigatorio")

        composeTestRule.setContent {
            GenderSelector(
                selectedGender = {},
                clearSelection = {true},
                error)
        }
        composeTestRule.onNodeWithText(error.toString())
    }
    @Test
    fun nextButton_should_deactivate_when_inputText_and_GenderButtons_areEmpty(){
        val name = "a"
        val errorGender = listOf("*Campo Obrigatorio")
        val nameError = listOf("Adicione um nome entre 2 a 30 caracteres")

        composeTestRule.setContent {
            DashedInputText(
                textValue = name,
                textError = nameError,
                onEvent = {},
                isError = true
            )

            GenderSelector(
                selectedGender = {},
                clearSelection = {true},
                errorGender
            )

            Button3(
                submit = { /*TODO*/ },
                enableButton = false, // Define o botão como desativado
            )
        }

        composeTestRule.onNodeWithText("Adicione um nome entre 2 a 30 caracteres").assertExists()
        composeTestRule.onNodeWithText(errorGender.toString())

        composeTestRule.onNodeWithTag("button3_test").assertIsNotEnabled()


    }
    @Test
    fun enable_nextButton_when_both_fields_are_not_empty(){
        val name = "Bolinha"

        composeTestRule.setContent {
            DashedInputText(
                textValue = name,
                onEvent = {},
                isError = false
            )

            GenderSelector(
                selectedGender = {"M"},
                clearSelection = {false},
            )
            Button3(
                submit = { /*TODO*/ },
                enableButton = true, // Define o botão como desativado
            )
        }

        composeTestRule.onNodeWithTag("dashedInputField_test").performTextInput(name)
        composeTestRule.onNodeWithTag("genderButtons_test").performClick()

        composeTestRule.onNodeWithTag("button3_test").assertIsEnabled()
        composeTestRule.onNodeWithTag("button3_test").performClick()
    }

}