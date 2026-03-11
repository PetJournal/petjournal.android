package com.soujunior.petjournal.ui.screensapp.screenTasks.registerTaskScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.components.Button3
import com.soujunior.petjournal.ui.components.GroupSelectableButton
import com.soujunior.petjournal.ui.components.InputText
import com.soujunior.petjournal.ui.components.NavigationBar
import com.soujunior.petjournal.ui.components.PetFilterList
import com.soujunior.petjournal.ui.components.ScaffoldCustom
import com.soujunior.petjournal.ui.components.TextFieldCustom
import com.soujunior.petjournal.ui.components.TransactionTypeSelector
import com.soujunior.petjournal.ui.components.task.OneOffTask
import com.soujunior.petjournal.ui.components.task.RecurringTask
import com.soujunior.petjournal.ui.model.SelectableButtonInfo
import com.soujunior.petjournal.ui.model.TagAction
import com.soujunior.petjournal.ui.screensapp.screenTasks.registerTaskScreen.viewmodel.FakeRegisterTaskViewModel
import com.soujunior.petjournal.ui.screensapp.screenTasks.registerTaskScreen.viewmodel.RegisterTaskViewModel
import com.soujunior.petjournal.ui.theme.ColorCustom
import com.soujunior.petjournal.ui.theme.PetJournalTheme
import com.soujunior.petjournal.ui.util.TransactionType
import ir.kaaveh.sdpcompose.sdp
import org.koin.androidx.compose.getViewModel

@Composable
fun RegisterTaskScreen(
    navController: NavController,
    viewModel: RegisterTaskViewModel =
        if (LocalInspectionMode.current) {
            FakeRegisterTaskViewModel()
        } else {
            getViewModel()
        },
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val ob = remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf<TransactionType?>(null) }

    ScaffoldCustom(
        modifier =
            Modifier
                .navigationBarsPadding()
                .fillMaxSize(),
        navigationUp = navController,
        bottomNavigationBar = { NavigationBar(navController) },
        showTopBar = true,
        showButtonToReturn = true,
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
                        GroupSelectableButton(
                            listOfTags = state.listTag,
                            isLoading = state.isLoadingListTag || state.isLoadingAll,
                            showButton = !state.isLoadingListTag,
                            onAction = {
                                when (it) {
                                    is TagAction.Create -> {
                                        viewModel.onEvent(
                                            RegisterTaskEvent.OnCreateTag(
                                                name = it.name,
                                                color =
                                                    it.color
                                                        .toArgb()
                                                        .toUInt()
                                                        .toString(16)
                                                        .uppercase(),
                                            ),
                                        )
                                    }
                                    is TagAction.Delete -> {
                                        viewModel.onEvent(
                                            event = RegisterTaskEvent.OnDeleteTag(it.id),
                                        )
                                    }
                                    is TagAction.Update -> {
                                        viewModel.onEvent(
                                            event =
                                                RegisterTaskEvent.OnUpdateTag(
                                                    id = it.id,
                                                    name = it.name,
                                                    color =
                                                        it.color
                                                            .toArgb()
                                                            .toUInt()
                                                            .toString(16)
                                                            .uppercase(),
                                                ),
                                        )
                                    }
                                }
                            },
                        )
                    }
                    item {
                        InputText(
                            isLoading = state.isLoadingTaskName || state.isLoadingAll,
                            modifier = Modifier.testTag("inputFieldTag"),
                            placeholderText = stringResource(R.string.enter_task_name_here),
                            titleText = stringResource(R.string.task_name),
                            textValue = state.taskName,
                            onEvent = {
                                viewModel.onEvent(
                                    RegisterTaskEvent.OnName(it),
                                )
                            },
                        )
                    }
                    item {
                        TextFieldCustom(
                            isLoading = state.isLoadingTaskDescription || state.isLoadingAll,
                            title = stringResource(R.string.label_description),
                            placeholder = stringResource(R.string.enter_the_task_description_here),
                            value = state.taskDescription,
                            onValueChange = {
                                viewModel.onEvent(
                                    RegisterTaskEvent.OnDescription(it),
                                )
                            },
                        )
                    }
                    item {
                        PetFilterList(
                            listPet = state.listPets,
                            selectedIds = state.selectedPet,
                            isLoading = state.isLoadingListPet || state.isLoadingAll,
                            onSelectionChanged = {
                                viewModel.onEvent(RegisterTaskEvent.OnPetList(it))
                            },
                        )
                    }
                    item {
                        Column {
                            TransactionTypeSelector(
                                isLoading = !state.isLoadingAll,
                                transactionTypeSelected = state.selectedTransactionType,
                                onSelectionChanged = { type ->
                                    type?.let {
                                        viewModel.onEvent(
                                            RegisterTaskEvent.OnChangeTransactionType(type),
                                        )
                                    }
                                },
                            )

                            Spacer(modifier = Modifier.padding(bottom = 16.dp))

                            when (state.selectedTransactionType) {
                                TransactionType.Recurrent -> {
                                    RecurringTask(
                                        onSelectedPeriod = {
                                            viewModel.onEvent(
                                                RegisterTaskEvent.OnPeriodType(it),
                                            )
                                        },
                                        selectedPeriod = state.periodType,
                                        onAmPmSelector = {
                                            it?.let {
                                                viewModel.onEvent(
                                                    RegisterTaskEvent.OnAmPm(it),
                                                )
                                            }
                                        },
                                        selectedAmPm = state.amPmSelected,
                                        onTime = { hour, minute ->
                                            viewModel.onEvent(
                                                RegisterTaskEvent.OnTimeChange(
                                                    Pair(hour, minute),
                                                ),
                                            )
                                        },
                                        time = state.timeSelected,
                                        activeMonths = setOf(),
                                        onWeekDaySelected = {
                                        },
                                        onDaySelected = {
                                        },
                                    )
                                }

                                TransactionType.OneOff -> {
                                    OneOffTask(
                                        selectedAmPm = state.amPmSelected,
                                        onTime = { hour, minute ->
                                            viewModel.onEvent(
                                                RegisterTaskEvent.OnTimeChange(
                                                    Pair(hour, minute),
                                                ),
                                            )
                                        },
                                        onAmPmSelector = {
                                            it?.let {
                                                viewModel.onEvent(
                                                    RegisterTaskEvent.OnAmPm(it),
                                                )
                                            }
                                        },
                                        onDateSelected = {
                                        },
                                    )
                                }
                            }
                        }
                    }
                    item {
                        TextFieldCustom(
                            isLoading = true,
                            title = stringResource(R.string.label_observation),
                            placeholder = stringResource(R.string.enter_your_observation_here),
                            value = ob.value,
                            onValueChange = { o ->
                                ob.value = o
                            },
                        )
                    }
                    item {
                        val isLoading = true
                        if (!isLoading) {
                            Button3(
                                submit = {
                                },
                                enableButton = true,
                                text = stringResource(R.string.label_save_task),
                            )
                        }
                    }
                },
            )
        },
    )
}

@Composable
fun OnChangeTransactionType(x0: TransactionType?) {
    TODO("Not yet implemented")
}

private val listOfTasks =
    listOf(
        SelectableButtonInfo(
            "1",
            "Vacinas",
            ColorCustom.color_selectable_button_1,
        ),
        SelectableButtonInfo(
            "2",
            "Consultas",
            ColorCustom.color_selectable_button_2,
        ),
        SelectableButtonInfo(
            "3",
            "Remédios",
            ColorCustom.color_selectable_button_3,
        ),
        SelectableButtonInfo(
            "4",
            "Banho",
            ColorCustom.color_selectable_button_4,
        ),
        SelectableButtonInfo(
            "5",
            "Comida",
            ColorCustom.color_selectable_button_5,
        ),
        SelectableButtonInfo(
            "6",
            "Passeio",
            ColorCustom.color_selectable_button_6,
        ),
    )

@Preview(showBackground = true, showSystemUi = true, device = "id:pixel_4_xl")
@Composable
fun ScreenRegisterTaskPreview() {
    val nav = rememberNavController()
    RegisterTaskScreen(nav)
}

@Preview(showBackground = true)
@Composable
fun GroupSelectableButtonPreview() {
    PetJournalTheme {
        GroupSelectableButton(
            modifier = Modifier,
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
