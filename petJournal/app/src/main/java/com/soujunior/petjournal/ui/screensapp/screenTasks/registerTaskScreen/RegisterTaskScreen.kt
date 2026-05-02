package com.soujunior.petjournal.ui.screensapp.screenTasks.registerTaskScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
import com.soujunior.petjournal.ui.components.dialog.CardDialog
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
    val isLoadingAll = state.isLoadingListTag && state.isLoadingListPet

    Column(
        modifier = Modifier.background(color = MaterialTheme.colorScheme.onPrimary),
    ) {
        ScaffoldCustom(
            modifier =
                Modifier
                    .fillMaxSize()
                    .navigationBarsPadding()
                    .statusBarsPadding(),
            navigationUp = navController,
            bottomNavigationBar = {
                NavigationBar(
                    navController,
                    modifier =
                        Modifier
                            .navigationBarsPadding()
                            .statusBarsPadding(),
                )
            },
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
                                isLoading = isLoadingAll,
                                selectedTag = state.selectedTag,
                                showButton = !state.isLoadingListTag,
                                onSelection = {
                                    viewModel.onEvent(RegisterTaskEvent.OnSelectTag(it))
                                },
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
                                isLoading = isLoadingAll,
                                modifier = Modifier.testTag("inputFieldTag"),
                                placeholderText = stringResource(R.string.enter_task_name_here),
                                titleText = stringResource(R.string.task_name),
                                textValue = state.taskName,
                                onEvent = { input ->
                                    viewModel.onEvent(
                                        RegisterTaskEvent.OnName(input.take(30)),
                                    )
                                },
                            )
                        }
                        item {
                            TextFieldCustom(
                                isLoading = isLoadingAll,
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
                                isLoading = isLoadingAll,
                                listPet = state.listPets,
                                selectedIds = state.selectedPet,
                                onSelectionChanged = {
                                    viewModel.onEvent(RegisterTaskEvent.OnPetList(it))
                                },
                            )
                        }
                        item {
                            Column {
                                TransactionTypeSelector(
                                    isLoading = isLoadingAll,
                                    transactionTypeSelected = state.selectedTransactionType,
                                    onSelectionChanged = { type ->
                                        type?.let {
                                            viewModel.onEvent(
                                                RegisterTaskEvent.OnChangeTransactionType(type),
                                            )
                                        }
                                    },
                                )

                                if (!isLoadingAll) {
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
                                                activeMonths = state.activeMonths,
                                                onWeekDaySelected = {
                                                    viewModel.onEvent(
                                                        RegisterTaskEvent.OnDayOfWeekChanged(
                                                            it,
                                                        ),
                                                    )
                                                },
                                                daySelected = state.daySelected,
                                                selectedDaysOfWeek = state.selectedDaysOfWeek,
                                                onDaySelected = {
                                                    viewModel.onEvent(
                                                        RegisterTaskEvent.OnDayChanged(
                                                            it,
                                                        ),
                                                    )
                                                },
                                            )
                                        }

                                        TransactionType.OneOff -> {
                                            OneOffTask(
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
                                                dateSelected = state.dateSelected,
                                                onDateSelected = {
                                                    viewModel.onEvent(
                                                        RegisterTaskEvent.OnDateChanged(
                                                            it,
                                                        ),
                                                    )
                                                },
                                            )
                                        }
                                    }
                                }
                            }
                        }
                        item {
                            TextFieldCustom(
                                isLoading = isLoadingAll,
                                title = stringResource(R.string.label_observation),
                                placeholder = stringResource(R.string.enter_your_observation_here),
                                value = state.observation,
                                onValueChange = { obs ->
                                    viewModel.onEvent(RegisterTaskEvent.OnObservation(obs))
                                },
                            )
                        }
                        item {
                            if (!isLoadingAll) {
                                Row(
                                    modifier = Modifier.fillMaxSize(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                ) {
                                    Text(
                                        text = "Sincronizar com a nuvem",
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = MaterialTheme.colorScheme.onSurface,
                                    )
                                    Switch(
                                        checked = state.sendToApi,
                                        onCheckedChange = { checked ->
                                            viewModel.onEvent(RegisterTaskEvent.OnSendToApiChanged(checked))
                                        },
                                    )
                                }
                                Spacer(Modifier.padding(top = 16.dp))
                                Row(
                                    modifier = Modifier.fillMaxSize(),
                                    horizontalArrangement = Arrangement.Center,
                                ) {
                                    Button3(
                                        submit = {
                                            viewModel.onEvent(RegisterTaskEvent.Submit)
                                        },
                                        enableButton = true,
                                        text = stringResource(R.string.label_save_task),
                                    )
                                }
                                Spacer(Modifier.padding(top = 20.dp))
                            }
                        }
                    },
                )
                if (state.showDialogSuccess) {
                    CardDialog(
                        title = "Sucesso ao criar tarefa",
                        textBottomButton = "Voltar",
                        textTopButton = "Criar outra tarefa",
                        onButtonTopClick = {
                            viewModel.onEvent(RegisterTaskEvent.OnCardDialogAddNewTask)
                        },
                        onButtonBottomClick = {
                            navController.navigateUp()
                        },
                    )
                }

                if (state.showDialogError) {
                    CardDialog(
                        title = "Ocorreu um erro ao criar tarefa",
                        textBottomButton = stringResource(R.string.return_button_text),
                        onButtonBottomClick = {
                            viewModel.onEvent(RegisterTaskEvent.OnCardDialogError)
                        },
                        subText = state.cardDialogMessage,
                    )
                }
            },
        )
    }
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
            listOf(),
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
