package com.soujunior.petjournal.ui.screensapp.screensPets.registerTaskScreen.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.soujunior.petjournal.ui.components.GroupSelectableButton
import com.soujunior.petjournal.ui.components.InputText
import com.soujunior.petjournal.ui.components.PetFilterList
import com.soujunior.petjournal.ui.components.Pets
import com.soujunior.petjournal.ui.components.SelectableButtonInfo
import com.soujunior.petjournal.ui.components.TextFieldCustom
import com.soujunior.petjournal.ui.components.TransactionTypeSelector
import com.soujunior.petjournal.ui.components.task.OneOffTask
import com.soujunior.petjournal.ui.components.task.RecurringTask
import com.soujunior.petjournal.ui.theme.ColorCustom
import com.soujunior.petjournal.ui.util.TransactionType
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ScreenRegisterTaskTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val pets =
        listOf(
            Pets(id = 1, name = "Rex"),
            Pets(id = 2, name = "Mia"),
            Pets(id = 3, name = "Thor"),
        )

    private val sampleListOfTasks =
        listOf(
            SelectableButtonInfo("Vacinas", ColorCustom.color_selectable_button_1),
            SelectableButtonInfo("Consultas", ColorCustom.color_selectable_button_2),
            SelectableButtonInfo("Remédios", ColorCustom.color_selectable_button_3),
        )

    @Test
    fun groupSelectableButton_displaysTitleCorrectly() {
        composeTestRule.setContent {
            GroupSelectableButton(
                listOfTags = sampleListOfTasks,
                onSelection = {},
            )
        }

        composeTestRule.onNodeWithText("Essa tarefa é...")
            .assertIsDisplayed()
    }

    @Test
    fun groupSelectableButton_displaysAllButtons() {
        composeTestRule.setContent {
            GroupSelectableButton(
                listOfTags = sampleListOfTasks,
                onSelection = {},
            )
        }

        sampleListOfTasks.forEach { buttonInfo ->
            composeTestRule.onNodeWithText(buttonInfo.title).assertIsDisplayed()
        }
    }

    @Test
    fun groupSelectableButton_selectsAndDeselectsButtonCorrectly() {
        composeTestRule.setContent {
            GroupSelectableButton(
                listOfTags = sampleListOfTasks,
                onSelection = {},
            )
        }

        val firstButton = sampleListOfTasks[0]

        composeTestRule.onNodeWithText(firstButton.title).performClick()

        composeTestRule.onNodeWithText(firstButton.title).performClick()
    }

    @Test
    fun groupSelectableButton_selectsOnlyOneButtonAtATime_ifLogicImpliesThat() {
        composeTestRule.setContent {
            GroupSelectableButton(
                listOfTags = sampleListOfTasks,
                onSelection = {},
            )
        }

        val firstButton = sampleListOfTasks[0]
        val secondButton = sampleListOfTasks[1]

        composeTestRule.onNodeWithText(firstButton.title).performClick()

        composeTestRule.onNodeWithText(secondButton.title).performClick()
    }

    @Test
    fun inputText_displaysTitleCorrectly() {
        val testTitle = "Nome da Tarefa"
        composeTestRule.setContent {
            InputText(
                titleText = testTitle,
                textValue = "",
                onEvent = {},
            )
        }

        composeTestRule.onNodeWithText(testTitle).assertIsDisplayed()
    }

    @Test
    fun inputText_displaysPlaceholder_whenTextValueIsEmptyAndNotMasked() {
        val testPlaceholder = "Digite o nome da tarefa"
        composeTestRule.setContent {
            InputText(
                placeholderText = testPlaceholder,
                textValue = "",
                onEvent = {},
            )
        }

        composeTestRule.onNodeWithText(testPlaceholder).assertIsDisplayed()
    }

    @Test
    fun inputText_doesNotDisplayPlaceholder_whenTextValueIsNotEmpty() {
        val testPlaceholder = "Digite o nome da tarefa"
        composeTestRule.setContent {
            InputText(
                placeholderText = testPlaceholder,
                textValue = "Minha Tarefa",
                onEvent = {},
            )
        }

        composeTestRule.onNodeWithText(testPlaceholder).assertDoesNotExist()
    }

    @Test
    fun inputText_displaysErrorMessages_whenTextErrorIsProvided() {
        val errorMessages = listOf("Nome muito curto", "Nome inválido")
        composeTestRule.setContent {
            InputText(
                textValue = "Ops",
                isError = true,
                textError = errorMessages,
                onEvent = {},
            )
        }
        errorMessages.forEach { errorMessage ->
            composeTestRule.onNodeWithText(errorMessage).assertIsDisplayed()
        }
    }

    private val title = "Descrição"
    private val placeholder = "Digite a descrição aqui..."

    @Test
    fun shouldDisplayTitleAndPlaceholder() {
        composeTestRule.setContent {
            TextFieldCustom(
                title = title,
                placeholder = placeholder,
                value = "",
                onValueChange = {},
            )
        }

        composeTestRule.onNodeWithText(title).assertIsDisplayed()

        composeTestRule.onNodeWithText(placeholder).assertIsDisplayed()
    }

    @Test
    fun shouldUpdateTextWhenTyping() {
        var textValue = ""
        composeTestRule.setContent {
            TextFieldCustom(
                title = title,
                placeholder = placeholder,
                value = textValue,
                onValueChange = { textValue = it },
            )
        }

        val inputText = "Descrição do produto"

        composeTestRule.onNode(
            hasSetTextAction(),
        ).performTextInput(inputText)

        assert(textValue == inputText)
    }

    @Test
    fun shouldDisplayTitleAndAllOption() {
        composeTestRule.setContent {
            PetFilterList(listPet = pets)
        }

        composeTestRule.onNodeWithText("Quais pets precisam dessa tarefa?").assertIsDisplayed()

        composeTestRule.onNodeWithText("Todos").assertIsDisplayed()
    }

    @Test
    fun shouldDisplayAllPetsFromList() {
        composeTestRule.setContent {
            PetFilterList(listPet = pets)
        }

        pets.forEach { pet ->
            composeTestRule.onNodeWithText(pet.name!!).assertIsDisplayed()
        }
    }

    @Test
    fun shouldSelectAndUnselectPet() {
        var selected: String? = null
        composeTestRule.setContent {
            PetFilterList(
                listPet = pets,
                onSelectedPet = { selected = it },
            )
        }

        composeTestRule.onNodeWithText("Rex").performClick()
        assert(selected == "Rex")

        composeTestRule.onNodeWithText("Rex").performClick()
        assert(selected == "")
    }

    @Test
    fun shouldSelectAllOption() {
        var selected: String? = null
        composeTestRule.setContent {
            PetFilterList(
                listPet = pets,
                onSelectedPet = { selected = it },
            )
        }

        composeTestRule.onNodeWithText("Todos").performClick()
        assert(selected == "Todos")

        composeTestRule.onNodeWithText("Todos").performClick()
        assert(selected == "")
    }

    @Test
    fun shouldShowRecurringTaskWhenRecurrentSelected() {
        composeTestRule.setContent {
            var selectedType: TransactionType? = null

            Column {
                TransactionTypeSelector(
                    onSelectionChanged = { type ->
                        selectedType = type
                    },
                )

                when (selectedType) {
                    TransactionType.Recurrent -> {
                        RecurringTask(
                            setOf(),
                            onAmPmSelector = {},
                            onTime = { _, _ -> },
                            onWeekDaySelected = {},
                            onDaySelected = {},
                        )
                    }

                    TransactionType.OneOff -> {
                        OneOffTask(
                            onDateSelected = {},
                            onAmPmSelector = {},
                            onTime = { _, _ -> },
                        )
                    }

                    null -> {}
                }
            }
        }

        composeTestRule.onNodeWithText("Recorrente").performClick()
    }

    @Test
    fun test_TransactionTypeSelector_TogglesConditionalUI_Correctly() {
        composeTestRule.setContent {
            var selectedType: TransactionType? = null

            Column {
                TransactionTypeSelector(
                    onSelectionChanged = { type ->
                        selectedType = type
                    },
                )

                when (selectedType) {
                    TransactionType.Recurrent -> {
                        Box(modifier = Modifier.testTag("RecurringTaskComponent")) {
                            RecurringTask(
                                setOf(),
                                onAmPmSelector = {},
                                onTime = { _, _ -> },
                                onWeekDaySelected = {},
                                onDaySelected = {},
                            )
                        }
                    }

                    TransactionType.OneOff -> {
                        Box(modifier = Modifier.testTag("OneOffTaskComponent")) {
                            OneOffTask(
                                onDateSelected = {},
                                onAmPmSelector = {},
                                onTime = { _, _ -> },
                            )
                        }
                    }

                    null -> {}
                }
            }
        }

        composeTestRule.onNodeWithTag("RecurringTaskComponent").assertDoesNotExist()
        composeTestRule.onNodeWithTag("OneOffTaskComponent").assertDoesNotExist()

        composeTestRule.onNodeWithTag("TaskRecurrent").performClick()

        composeTestRule.onNodeWithTag("RecurringTaskComponent").assertIsDisplayed()
        composeTestRule.onNodeWithTag("OneOffTaskComponent").assertDoesNotExist()

        composeTestRule.onNodeWithTag("TaskOneOff").performClick()

        composeTestRule.onNodeWithTag("RecurringTaskComponent").assertDoesNotExist()
        composeTestRule.onNodeWithTag("OneOffTaskComponent").assertIsDisplayed()
    }

    @Test
    fun shouldShowOneOffTaskWhenOneOffSelected() {
        composeTestRule.setContent {
            var selectedType: TransactionType? = null

            Column {
                TransactionTypeSelector(
                    onSelectionChanged = { type ->
                        selectedType = type
                    },
                )

                when (selectedType) {
                    TransactionType.Recurrent -> {
                        RecurringTask(
                            setOf(),
                            onAmPmSelector = {},
                            onTime = { _, _ -> },
                            onWeekDaySelected = {},
                            onDaySelected = {},
                        )
                    }

                    TransactionType.OneOff -> {
                        OneOffTask(
                            onDateSelected = {},
                            onAmPmSelector = {},
                            onTime = { _, _ -> },
                        )
                    }

                    null -> {}
                }
            }
        }

        composeTestRule.onNodeWithText("Pontual").performClick()
    }

    @Test
    fun shouldUpdateObservationText() {
        composeTestRule.setContent {
            val ob = remember { mutableStateOf("") }

            TextFieldCustom(
                title = "Observação",
                placeholder = "Digite aqui a sua observação",
                value = ob.value,
                onValueChange = { ob.value = it },
            )
        }

        composeTestRule.onNodeWithText("Digite aqui a sua observação").assertIsDisplayed()

        val text = "Precisa dar o remédio às 14h"
        composeTestRule.onNodeWithText("Digite aqui a sua observação").performTextInput(text)
    }

    @Test
    fun test_TransactionTypeSelector_ShowsConditionalUI() {
        composeTestRule.onNodeWithTag("RecurringTaskComponent").assertDoesNotExist()

        composeTestRule.onNodeWithTag("TaskRecurrent").performClick()

        composeTestRule.onNodeWithTag("RecurringTaskComponent").assertIsDisplayed()
        composeTestRule.onNodeWithTag("OneOffTaskComponent").assertDoesNotExist()
    }
}
