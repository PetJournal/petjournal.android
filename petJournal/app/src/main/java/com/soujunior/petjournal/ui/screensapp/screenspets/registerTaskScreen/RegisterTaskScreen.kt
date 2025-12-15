package com.soujunior.petjournal.ui.screensapp.screenspets.registerTaskScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.components.Button3
import com.soujunior.petjournal.ui.components.GroupSelectableButton
import com.soujunior.petjournal.ui.components.InputText
import com.soujunior.petjournal.ui.components.PetFilterList
import com.soujunior.petjournal.ui.components.Pets
import com.soujunior.petjournal.ui.components.ScaffoldCustom
import com.soujunior.petjournal.ui.components.SelectableButtonInfo
import com.soujunior.petjournal.ui.components.TextFieldCustom
import com.soujunior.petjournal.ui.components.TransactionTypeSelector
import com.soujunior.petjournal.ui.components.task.OneOffTask
import com.soujunior.petjournal.ui.components.task.RecurringTask
import com.soujunior.petjournal.ui.theme.ColorCustom
import com.soujunior.petjournal.ui.theme.PetJournalTheme
import com.soujunior.petjournal.ui.util.TransactionType
import ir.kaaveh.sdpcompose.sdp

@Composable
fun RegisterTaskScreen(navController: NavController) {
    val listOfTasks =
        listOf(
            SelectableButtonInfo(
                stringResource(R.string.label_selectable_button_vaccines),
                ColorCustom.color_selectable_button_1,
            ),
            SelectableButtonInfo(
                stringResource(R.string.label_selectable_button_consultations),
                ColorCustom.color_selectable_button_2,
            ),
            SelectableButtonInfo(
                stringResource(R.string.label_selectable_button_medicine),
                ColorCustom.color_selectable_button_3,
            ),
            SelectableButtonInfo(
                stringResource(R.string.label_selectable_button_bath),
                ColorCustom.color_selectable_button_4,
            ),
            SelectableButtonInfo(
                stringResource(R.string.label_selectable_button_food),
                ColorCustom.color_selectable_button_5,
            ),
            SelectableButtonInfo(
                stringResource(R.string.label_selectable_button_pet_walk),
                ColorCustom.color_selectable_button_6,
            ),
        )

    val listPet =
        listOf(
            Pets(
                id = 1,
                imageRes = painterResource(R.drawable.image_jujuba),
                name = "Jujuba",
            ),
            Pets(
                id = 2,
                imageRes = painterResource(R.drawable.image_alfredo),
                name = "Alfredo",
            ),
            Pets(
                id = 3,
                imageRes = painterResource(R.drawable.image_alfredo),
                name = "Alfredo",
            ),
            Pets(
                id = 4,
                imageRes = painterResource(R.drawable.image_alfredo),
                name = "Alfredo",
            ),
            Pets(
                id = 5,
                imageRes = painterResource(R.drawable.image_alfredo),
                name = "Alfredo",
            ),
            Pets(
                id = 6,
                imageRes = painterResource(R.drawable.image_alfredo),
                name = "Alfredo",
            ),
            Pets(
                id = 7,
                imageRes = painterResource(R.drawable.image_alfredo),
                name = "Alfredo",
            ),
            Pets(
                id = 8,
                imageRes = painterResource(R.drawable.image_alfredo),
                name = "Alfredo",
            ),
            Pets(
                id = 9,
                imageRes = painterResource(R.drawable.image_alfredo),
                name = "Alfredo",
            ),
        )

    val nameTask = remember { mutableStateOf("") }
    val desc = remember { mutableStateOf("") }
    val ob = remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf<TransactionType?>(null) }

    ScaffoldCustom(
        modifier =
            Modifier.navigationBarsPadding()
                .fillMaxSize(),
        navigationUp = navController,
        showTopBar = true,
        // todo: o valor desse Title bar precisa ser passado por parametro,
        // assim ele se comportara tanto como "Nova tarefa" quanto "Editar tarefa".
        titleTopBar = stringResource(R.string.label_new_task),
        showBottomBarNavigation = true,
        contentToUse = { paddingValues ->
            Image(
                painter = painterResource(R.drawable.rastro),
                contentDescription = null,
                modifier =
                    Modifier
                        .fillMaxSize()
                        .offset(y = 300.sdp),
            )
            LazyColumn(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                verticalArrangement = Arrangement.spacedBy(12.sdp),
                contentPadding = PaddingValues(horizontal = 14.sdp),
                content = {
                    item {
                        GroupSelectableButton(listOfTasks)
                    }
                    item {
                        InputText(
                            modifier = Modifier.testTag("inputFieldTag"),
                            placeholderText = stringResource(R.string.enter_task_name_here),
                            titleText = stringResource(R.string.task_name),
                            textValue = nameTask.value,
                            onEvent = { t ->
                                nameTask.value = t
                            },
                        )
                    }
                    item {
                        TextFieldCustom(
                            title = stringResource(R.string.label_description),
                            placeholder = stringResource(R.string.enter_the_task_description_here),
                            value = desc.value,
                            onValueChange = { d ->
                                desc.value = d
                            },
                        )
                    }
                    item {
                        PetFilterList(
                            listPet,
                            onSelectedPet = {
                            },
                        )
                    }
                    item {
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
                                        onAmPmSelector = {
                                        },
                                        onTime = { hour, minute ->
                                        },
                                        onWeekDaySelected = {
                                        },
                                        onDaySelected = {
                                        },
                                    )
                                }

                                TransactionType.OneOff -> {
                                    OneOffTask(
                                        onDateSelected = {
                                        },
                                        onAmPmSelector = {
                                        },
                                        onTime = { hour, minute ->
                                        },
                                    )
                                }

                                null -> {}
                            }
                        }
                    }
                    item {
                        TextFieldCustom(
                            title = stringResource(R.string.label_observation),
                            placeholder = stringResource(R.string.enter_your_observation_here),
                            value = ob.value,
                            onValueChange = { o ->
                                ob.value = o
                            },
                        )
                    }
                    item {
                        Button3(submit = { /*TODO*/ }, enableButton = true, text = stringResource(R.string.label_save_task))
                    }
                },
            )
        },
    )
}

@Preview(showBackground = true, showSystemUi = true, device = "id:pixel_4_xl")
@Composable
fun ScreenRegisterTaskPreview() {
    val nav = rememberNavController()
    RegisterTaskScreen(nav)
}

private val listOfTasks =
    listOf(
        SelectableButtonInfo(
            "Vacinas",
            ColorCustom.color_selectable_button_1,
        ),
        SelectableButtonInfo(
            "Consultas",
            ColorCustom.color_selectable_button_2,
        ),
        SelectableButtonInfo(
            "Remédios",
            ColorCustom.color_selectable_button_3,
        ),
        SelectableButtonInfo(
            "Banho",
            ColorCustom.color_selectable_button_4,
        ),
        SelectableButtonInfo(
            "Comida",
            ColorCustom.color_selectable_button_5,
        ),
        SelectableButtonInfo(
            "Passeio",
            ColorCustom.color_selectable_button_6,
        ),
    )

@Preview(showBackground = true)
@Composable
fun GroupSelectableButtonPreview() {
    PetJournalTheme {
        GroupSelectableButton(
            listOfTasks,
            onSelection = {
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun InputTextTaskNamePreview() {
    val nameTask = remember { mutableStateOf("") }
    PetJournalTheme {
        InputText(
            modifier = Modifier.testTag("inputFieldTag"),
            placeholderText = stringResource(R.string.enter_task_name_here),
            titleText = stringResource(R.string.task_name),
            textValue = nameTask.value,
            onEvent = { t ->
                nameTask.value = t
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TextFieldCustomDescriptionPreview() {
    val desc = remember { mutableStateOf("") }
    PetJournalTheme {
        TextFieldCustom(
            title = stringResource(R.string.label_description),
            placeholder = stringResource(R.string.enter_the_task_description_here),
            value = desc.value,
            onValueChange = { d ->
                desc.value = d
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PetFilterListPreview() {
    val listPet =
        listOf(
            Pets(
                id = 1,
                imageRes = painterResource(R.drawable.image_jujuba),
                name = "Jujuba",
            ),
            Pets(
                id = 2,
                imageRes = painterResource(R.drawable.image_alfredo),
                name = "Alfredo",
            ),
            Pets(
                id = 3,
                imageRes = painterResource(R.drawable.image_alfredo),
                name = "Alfredo",
            ),
        )
    PetJournalTheme {
        PetFilterList(
            listPet,
            onSelectedPet = {
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TransactionTypeSelectorPreview() {
    var selectedType by remember { mutableStateOf<TransactionType?>(null) }
    PetJournalTheme {
        TransactionTypeSelector(
            onSelectionChanged = { type ->
                selectedType = type
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RecurringTaskPreview() {
    PetJournalTheme {
        RecurringTask(
            setOf(),
            onAmPmSelector = {
            },
            onTime = { hour, minute ->
            },
            onWeekDaySelected = {
            },
            onDaySelected = {
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun OneOffTaskPreview() {
    PetJournalTheme {
        OneOffTask(
            onDateSelected = {
            },
            onAmPmSelector = {
            },
            onTime = { hour, minute ->
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TextFieldCustomObservationPreview() {
    val ob = remember { mutableStateOf("") }
    PetJournalTheme {
        TextFieldCustom(
            title = stringResource(R.string.label_observation),
            placeholder = stringResource(R.string.enter_your_observation_here),
            value = ob.value,
            onValueChange = { o ->
                ob.value = o
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun Button3SaveTaskPreview() {
    PetJournalTheme {
        Button3(
            submit = { /*TODO*/ },
            enableButton = true,
            text = stringResource(R.string.label_save_task),
        )
    }
}
