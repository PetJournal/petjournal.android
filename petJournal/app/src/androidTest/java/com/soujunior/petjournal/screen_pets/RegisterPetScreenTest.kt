package com.soujunior.petjournal.screen_pets

import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.soujunior.domain.model.request.PetSizeItemModel
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.components.DateInputText
import com.soujunior.petjournal.ui.components.DropDown
import com.soujunior.petjournal.ui.components.DualActionButton
import com.soujunior.petjournal.ui.components.InputText
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNotSame
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test

class RegisterPetScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val mockItems = listOf(
        PetSizeItemModel("", "Golden Retriever", ""),
        PetSizeItemModel("", "Persa", ""),
        PetSizeItemModel("", "Calopsita", "")

    )

    private val mockSizes = listOf(
        PetSizeItemModel("", "Pequeno", ""),
        PetSizeItemModel("", "Médio", ""),
        PetSizeItemModel("", "Grande", "")
    )

    private val weightOptions = listOf(
        PetSizeItemModel("", "0-5 kg", ""),
        PetSizeItemModel("", "5-10 kg", ""),
        PetSizeItemModel("", "10-20 kg", ""),
        PetSizeItemModel("", "20+ kg", "")
    )

    private val mockPetTypes = listOf(
        PetSizeItemModel("", "Cachorro", ""),
        PetSizeItemModel("", "Gato", ""),
        PetSizeItemModel("", "Pássaro", "")
    )

    // Tests for name pet
    @Test
    fun inputText_SuccessCase_WhenValidPetNameEntered() {
        var actualText = ""
        val testPetName = "Rex"

        composeTestRule.setContent {
            InputText(
                titleText = stringResource(R.string.pet_name),
                placeholderText = stringResource(R.string.placeholder_name_pet),
                textValue = actualText,
                onEvent = { actualText = it }
            )
        }

        composeTestRule.onNodeWithTag("inputField_test")
            .performTextInput(testPetName)

        assertEquals(testPetName, actualText)
    }

    @Test
    fun inputText_ErrorCase_WhenValidationFails() {
        val errorMessage = "O nome do pet não pode estar vazio"

        composeTestRule.setContent {
            InputText(
                titleText = stringResource(R.string.pet_name),
                textValue = "",
                onEvent = {},
                isError = true,
                textError = listOf(errorMessage),
            )
        }

        composeTestRule.onNodeWithContentDescription("Erro")
            .assertExists()

        composeTestRule.onNodeWithText(errorMessage)
            .assertExists()
    }

    // Tests for breed
    @Test
    fun dropdown_ShowOptions_WhenClicked() {

        composeTestRule.setContent {
            DropDown(
                titleText = stringResource(R.string.breed),
                placeholderText = stringResource(R.string.placeholder_breed),
                dropdownItems = mockItems,
                onEvent = {},
                textValue = ""
            )
        }

        composeTestRule.onNodeWithText("Golden Retrivier").performClick()

        composeTestRule.onNodeWithText("Persa").assertExists()
        composeTestRule.onNodeWithText("Calopsita").assertExists()
    }

    @Test
    fun dropdown_UpdateSelection_WhenItemSelected() {
        var selectedValue = ""

        composeTestRule.setContent {
            DropDown(
                titleText = stringResource(R.string.breed),
                placeholderText = stringResource(R.string.placeholder_breed),
                dropdownItems = mockItems,
                onEvent = { selectedValue = it },
                textValue = selectedValue
            )
        }

        composeTestRule.onNodeWithText("Golden Retrivier").performClick()
        composeTestRule.onNodeWithText("Persa").performClick()

        assertTrue(selectedValue == "Persa")
    }

    @Test
    fun dropdown_ShowPlaceholder_WhenNoSelection() {
        composeTestRule.setContent {
            DropDown(
                placeholderText = stringResource(R.string.placeholder_breed),
                onEvent = {},
                textValue = ""
            )
        }

        composeTestRule.onNodeWithText("Golden Retrivier").assertExists()
    }

    // Tests for size
    @Test
    fun selectSize_ShouldUpdateValueAndCloseMenu() {
        var selectedSize = ""

        composeTestRule.setContent {
            DropDown(
                titleText = stringResource(R.string.size),
                placeholderText = stringResource(R.string.placeholder_size),
                dropdownItems = mockSizes,
                onEvent = { selectedSize = it },
                textValue = selectedSize
            )
        }

        composeTestRule.onNodeWithText("Pequeno (6kg a 14kg)").performClick()

        mockSizes.forEach {
            composeTestRule.onNodeWithText(it.name).assertExists()
        }

        composeTestRule.onNodeWithText("Médio").performClick()

        assertEquals("Médio", selectedSize)
    }

    @Test
    fun requiredField_ShouldShowErrorWhenEmpty() {
        val errorMessages = listOf("Campo obrigatório")

        composeTestRule.setContent {
            DropDown(
                isError = true,
                textError = errorMessages,
                onEvent = {},
                textValue = ""
            )
        }

        composeTestRule.onNodeWithText("Campo obrigatório").assertExists()
    }

    @Test
    fun emptyItems_ShouldNotOpenMenu() {
        composeTestRule.setContent {
            DropDown(
                dropdownItems = emptyList(),
                onEvent = {},
                textValue = ""
            )
        }

        composeTestRule.onNodeWithText("Porte do seu pet").performClick()
        composeTestRule.onNodeWithText("Pequeno").assertDoesNotExist()
    }

    // Tests for date
    @Test
    fun dateInput_SuccessCase_WhenValidDateEntered() {
        var actualText = ""
        val validDate = "31082023"

        composeTestRule.setContent {
            DateInputText(
                titleText = stringResource(R.string.pet_birth_date),
                placeholderText = stringResource(R.string.placeholder_text_DD_MM_YYYY),
                textValue = actualText,
                onEvent = { actualText = it }
            )
        }

        composeTestRule.onNodeWithTag("dateInputField")
            .performTextInput(validDate)

        assertEquals(validDate, actualText)
    }

    @Test
    fun dateInput_ErrorCase_WhenInvalidInput() {
        val errorMessage = "Data inválida"

        composeTestRule.setContent {
            DateInputText(
                textValue = "abc",
                isError = true,
                textError = listOf(errorMessage),
                onEvent = {}
            )
        }

        composeTestRule.onNodeWithContentDescription("Erro").assertExists()
        composeTestRule.onNodeWithText(errorMessage).assertExists()
    }

    @Test
    fun dateInput_LengthRestriction_WhenExceedsMaxLength() {
        var actualText = ""
        val longInput = "123456789"

        composeTestRule.setContent {
            DateInputText(
                textValue = actualText,
                onEvent = { actualText = it }
            )
        }

        composeTestRule.onNodeWithTag("dateInputField")
            .performTextInput(longInput)

        assertNotSame("12345678", actualText)
    }

    @Test
    fun dateInput_VisualFeedback_WhenPartiallyFilled() {
        var actualText = ""

        composeTestRule.setContent {
            DateInputText(
                textValue = actualText,
                onEvent = { actualText = it }
            )
        }

        composeTestRule.onNodeWithTag("dateInputField")
            .performTextInput("3108")

        assertTrue(actualText.length == 4)
    }

    @Test
    fun dateInput_ErrorState_WithCustomValidation() {
        val invalidDate = "99999999"
        val errorMessage = "Data inexistente"

        composeTestRule.setContent {
            DateInputText(
                textValue = invalidDate,
                isError = true,
                textError = listOf(errorMessage),
                onEvent = {}
            )
        }

        composeTestRule.onNodeWithText(errorMessage).assertExists()
        composeTestRule.onNodeWithContentDescription("Erro").assertExists()
    }

    // Tests for weight
    @Test
    fun dropdown_SelectWeight_UpdatesValueCorrectly() {
        var selectedWeight = ""

        composeTestRule.setContent {
            DropDown(
                titleText = stringResource(R.string.weight),
                placeholderText = stringResource(R.string.placeholder_weight),
                dropdownItems = weightOptions,
                onEvent = { selectedWeight = it },
                textValue = selectedWeight
            )
        }

        composeTestRule.onNodeWithText("4 kg").performClick()
        composeTestRule.onNodeWithText("5-10 kg").performClick()

        assertEquals("5-10 kg", selectedWeight)
    }

    @Test
    fun dropdown_ShowCorrectPlaceholder_WhenEmpty() {
        composeTestRule.setContent {
            DropDown(
                placeholderText = "Peso do seu pet",
                onEvent = {},
                textValue = ""
            )
        }

        composeTestRule.onNodeWithText("Peso do seu pet").assertExists()
    }

    // Tests for type
    @Test
    fun selectPetType_ShouldUpdateDisplayAndCallback() {
        var selectedType = ""

        composeTestRule.setContent {
            DropDown(
                titleText = stringResource(R.string.type),
                placeholderText = stringResource(R.string.placeholder_type),
                dropdownItems = mockPetTypes,
                onEvent = { selectedType = it },
                textValue = selectedType
            )
        }

        composeTestRule.onNodeWithText("Cachorro").performClick()
        composeTestRule.onNodeWithText("Gato").performClick()

        assertEquals("Gato", selectedType)
    }

    @Test
    fun invalidSelection_ShouldMaintainErrorState() {
        val errorMessage = "Tipo inválido"

        composeTestRule.setContent {
            DropDown(
                isError = true,
                textError = listOf(errorMessage),
                dropdownItems = mockPetTypes,
                onEvent = {},
                textValue = "Réptil"
            )
        }

        composeTestRule.onNodeWithText("Réptil").assertDoesNotExist()
        composeTestRule.onNodeWithText(errorMessage).assertExists()
    }

    // Tests for gender
    @Test
    fun dualButton_SuccessCase_WhenSelectingMale() {
        var maleSelected = false

        composeTestRule.setContent {
            DualActionButton(
                leftButtonText = stringResource(R.string.male),
                rightButtonText = stringResource(R.string.female),
                rightButtonSubmit = { maleSelected = true },
                leftButtonSubmit = { },
                enableButton = true
            )
        }

        composeTestRule.onNodeWithText("Macho").performClick()

        assertTrue(maleSelected)
    }

    @Test
    fun dualButton_SuccessCase_WhenSelectingFemale() {
        var femaleSelected = false

        composeTestRule.setContent {
            DualActionButton(
                leftButtonText = stringResource(R.string.male),
                rightButtonText = stringResource(R.string.female),
                leftButtonSubmit = { femaleSelected = true },
                rightButtonSubmit = {},
                enableButton = true
            )
        }

        composeTestRule.onNodeWithText("Fêmea").performClick()

        assertTrue(femaleSelected)
    }

    @Test
    fun dualButton_ErrorCase_WhenDisabled() {
        var clicks = 0

        composeTestRule.setContent {
            DualActionButton(
                leftButtonText = stringResource(R.string.male),
                rightButtonText = stringResource(R.string.female),
                enableButton = false,
                rightButtonSubmit = { clicks++ },
                leftButtonSubmit = { clicks++ }
            )
        }

        composeTestRule.onNodeWithText("Macho").assertIsNotEnabled().performClick()
        composeTestRule.onNodeWithText("Fêmea").assertIsNotEnabled().performClick()

        assertEquals(0, clicks)
    }

    // Tests for pet castrated
    @Test
    fun dualButton_CastrationFlow_WhenPetNotCastrated() {
        var petCastrate = false

        composeTestRule.setContent {
            DualActionButton(
                leftButtonText = stringResource(R.string.yes),
                rightButtonText = stringResource(R.string.no),
                rightButtonSubmit = {  },
                leftButtonSubmit = { petCastrate = false },
                enableButton = true,
            )
        }

        composeTestRule.onNodeWithText("Não").performClick()

        assertFalse(petCastrate)
    }

    @Test
    fun dualButton_PostCastrationFlow_WhenPetCastrated() {
        var petCastrate = false

        composeTestRule.setContent {
            DualActionButton(
                leftButtonText = stringResource(R.string.yes),
                rightButtonText = stringResource(R.string.no),
                rightButtonSubmit = { petCastrate = true },
                leftButtonSubmit = {  },
                enableButton = true,
            )
        }

        composeTestRule.onNodeWithText("Sim").performClick()

        assertTrue(petCastrate)
    }
}
