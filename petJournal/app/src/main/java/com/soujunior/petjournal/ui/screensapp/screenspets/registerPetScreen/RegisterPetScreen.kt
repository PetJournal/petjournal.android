package com.soujunior.petjournal.ui.screensapp.screenspets.registerPetScreen

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuItemColors
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.components.AlertText
import com.soujunior.petjournal.ui.components.Button3
import com.soujunior.petjournal.ui.components.DualActionButton
import com.soujunior.petjournal.ui.components.ImagePet
import com.soujunior.petjournal.ui.components.IndeterminateCircularIndicator
import com.soujunior.petjournal.ui.components.InputText
import com.soujunior.petjournal.ui.components.NavigationBar
import com.soujunior.petjournal.ui.components.ScaffoldCustom
import com.soujunior.petjournal.ui.components.dialog.CardDialog
import com.soujunior.petjournal.ui.components.mask.formatDate
import com.soujunior.petjournal.ui.states.TaskState
import com.soujunior.petjournal.ui.theme.ColorCustom
import com.soujunior.petjournal.ui.theme.ColorGrid
import com.soujunior.petjournal.ui.theme.PetJournalTheme
import com.soujunior.petjournal.ui.util.shimmerEffect
import ir.kaaveh.sdpcompose.sdp
import org.koin.androidx.compose.koinViewModel
import java.util.Calendar

@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun getPetRegisterViewModelForPreview(): PetRegisterViewModel {
    return if (LocalInspectionMode.current) {
        FakePetRegisterViewModel()
    } else {
        koinViewModel()
    }
}

@Composable
fun RegisterPetScreen(navController: NavController) {
    val viewModel: PetRegisterViewModel = getPetRegisterViewModelForPreview()
    val taskState by viewModel.taskState.collectAsState()
    val state = viewModel.stateUi.collectAsStateWithLifecycle()

    Column(modifier = Modifier) {
        ScaffoldCustom(
            modifier = Modifier.fillMaxSize().navigationBarsPadding().statusBarsPadding(),
            navigationUp = navController,
            showTopBar = true,
            showButtonToReturn = true,
            titleTopBar =
                if (!state.value.idPetSelected.isNullOrBlank()) {
                    stringResource(R.string.edit_pet_data)
                } else {
                    stringResource(R.string.add_new_pet)
                },
            showBottomBarNavigation = true,
            bottomNavigationBar = {
                NavigationBar(
                    navController = navController,
                    modifier = Modifier.navigationBarsPadding().statusBarsPadding(),
                )
            },
            contentToUse = { paddingValues ->
                if (taskState is TaskState.Loading) {
                    IndeterminateCircularIndicator(modifier = Modifier.align(CenterHorizontally))
                } else {
                    Image(
                        painter = painterResource(R.drawable.rastro),
                        contentDescription = null,
                        modifier =
                            Modifier
                                .fillMaxSize()
                                .offset(y = 400.sdp)
                                .align(AbsoluteAlignment.Left),
                    )
                    LazyColumn(
                        horizontalAlignment = CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.sdp),
                        modifier =
                            Modifier
                                .fillMaxSize()
                                .fillMaxHeight()
                                .padding(paddingValues)
                                .background(Color.Transparent),
                        contentPadding = PaddingValues(horizontal = 24.sdp),
                        content = {
                            item {
                                ImagePet(
                                    imagePath = state.value.petImage,
                                    onImageChanged = { newPath ->
                                        viewModel.onEvent(CreatePetEvent.OnInputImage(newPath))
                                    },
                                )
                            }
                            item {
                                InputText(
                                    modifier = Modifier,
                                    textTitleModifier = Modifier.padding(bottom = 4.sdp),
                                    textInputModifier = Modifier.testTag("inputField_test"),
                                    placeholderText = stringResource(R.string.placeholder_name_pet),
                                    titleText = stringResource(R.string.pet_name),
                                    textValue = state.value.petName ?: "",
                                    onEvent = {
                                        viewModel.onEvent(CreatePetEvent.OnInputName(it))
                                    },
                                )
                            }
                            item {
                                DropDown(
                                    textInputModifier = Modifier.padding(top = 4.sdp),
                                    placeholderText = stringResource(R.string.eg_cachorro),
                                    titleText = stringResource(R.string.type),
                                    textValue = state.value.selectedAnimalType ?: "",
                                    dropdownItems = state.value.listAnimalTypes,
                                    onEvent = {
                                        viewModel.onEvent(CreatePetEvent.OnTypeSelected(it))
                                    },
                                )
                            }
                            item {
                                DropDown(
                                    textInputModifier = Modifier,
                                    textTitleModifier = Modifier.padding(bottom = 4.sdp),
                                    placeholderText = stringResource(R.string.placeholder_race),
                                    titleText = stringResource(R.string.race),
                                    textValue = state.value.petRace ?: "",
                                    isLoading = state.value.isLoadingBreeds,
                                    dropdownItems = state.value.listRaceOnly,
                                    onEvent = {
                                        viewModel.onEvent(CreatePetEvent.OnInputRace(it))
                                    },
                                )
                            }
                            item {
                                DropDown(
                                    textInputModifier = Modifier,
                                    textTitleModifier = Modifier.padding(bottom = 4.sdp),
                                    placeholderText = stringResource(R.string.placeholder_size),
                                    titleText = stringResource(R.string.size),
                                    dropdownItems = state.value.listSizeOnly,
                                    isLoading = state.value.isLoadingSizes,
                                    textValue = state.value.petSize ?: "",
                                    onEvent = {
                                        viewModel.onEvent(CreatePetEvent.OnInputSize(it))
                                    },
                                )
                            }
                            item {
                                DateInputText(
                                    textInputModifier = Modifier,
                                    textTitleModifier = Modifier.padding(bottom = 4.sdp),
                                    titleText = stringResource(R.string.pet_birth_date),
                                    placeholderText = stringResource(R.string.placeholder_text_DD_MM_YYYY),
                                    textValue = state.value.petBirthday ?: "",
                                    onEvent = {
                                        viewModel.onEvent(CreatePetEvent.OnInputBirthday(it))
                                    },
                                    visualTransformation = { date -> formatDate(date) },
                                )
                            }
                            item {
                                Column {
                                    Row {
                                        Text(
                                            text = stringResource(R.string.pet_sex),
                                            textAlign = TextAlign.Start,
                                            color = MaterialTheme.colorScheme.scrim,
                                            style = MaterialTheme.typography.bodyMedium,
                                            fontWeight = FontWeight(500),
                                            modifier =
                                                Modifier
                                                    .padding(bottom = 8.sdp)
                                                    .fillMaxWidth(),
                                        )
                                    }
                                    DualActionButton(
                                        isLeftSelected = state.value.petGender == "M",
                                        isRightSelected = state.value.petGender == "F",
                                        leftButtonText = stringResource(R.string.male),
                                        rightButtonText = stringResource(R.string.female),
                                        leftButtonSubmit = {
                                            viewModel.onEvent(CreatePetEvent.OnInputSex("M"))
                                        },
                                        rightButtonSubmit = {
                                            viewModel.onEvent(CreatePetEvent.OnInputSex("F"))
                                        },
                                        enableButton = true,
                                        titleText = stringResource(R.string.pet_sex),
                                    )
                                }
                            }
                            item {
                                Column {
                                    Row {
                                        Text(
                                            text = stringResource(R.string.pet_registration),
                                            textAlign = TextAlign.Start,
                                            color = MaterialTheme.colorScheme.scrim,
                                            style = MaterialTheme.typography.bodyMedium,
                                            fontWeight = FontWeight(500),
                                            modifier =
                                                Modifier
                                                    .padding(bottom = 8.sdp)
                                                    .fillMaxWidth(),
                                        )
                                    }
                                    DualActionButton(
                                        isLeftSelected = state.value.petCastrated == true,
                                        isRightSelected = state.value.petCastrated == false,
                                        buttonModifier = Modifier,
                                        titleText = stringResource(R.string.castrated),
                                        leftButtonSubmit = { viewModel.onEvent(CreatePetEvent.OnInputCastrated(true)) },
                                        rightButtonSubmit = { viewModel.onEvent(CreatePetEvent.OnInputCastrated(false)) },
                                        enableButton = true,
                                        leftButtonText = stringResource(R.string.yes),
                                        rightButtonText = stringResource(R.string.no),
                                    )
                                }
                            }
                            item {
                                Spacer(modifier = Modifier.height(20.sdp))
                            }
                            item {
                                Button3(
                                    submit = { viewModel.onEvent(CreatePetEvent.OnSubmit) },
                                    enableButton = true,
                                    text = stringResource(R.string.save),
                                )
                            }
                        },
                    )
                    if (state.value.showDialogSuccess) {
                        CardDialog(
                            title =
                                if (!state.value.idPetSelected.isNullOrBlank()) {
                                    "Pet editado com sucesso!"
                                } else {
                                    stringResource(R.string.add_new_pet)
                                },
                            textTopButton = "Adicionar um novo pet",
                            onButtonTopClick = {
                                viewModel.onEvent(CreatePetEvent.OnCleanState)
                            },
                            onButtonBottomClick = {
                                navController.navigate("home")
                            },
                        )
                    }

                    if (state.value.showDialogError) {
                        CardDialog(
                            title = stringResource(R.string.error_occurred_while_adding_the_companion),
                            textBottomButton = stringResource(R.string.return_button_text),
                            onButtonBottomClick = {
                                viewModel.onEvent(CreatePetEvent.OnCloseDialogError)
                            },
                            subText = state.value.messageError,
                        )
                    }
                }
            },
        )
    }
}

@Preview(showBackground = true, showSystemUi = true, device = "id:pixel_4_xl")
@Composable
fun ScreenPreview() {
    val nav = rememberNavController()
    PetJournalTheme(
        content = {
            RegisterPetScreen(nav)
        },
    )
}

@Preview(showBackground = true)
@Composable
fun InputTextPreview() {
    PetJournalTheme {
        InputText(
            modifier = Modifier,
            textTitleModifier = Modifier.padding(bottom = 4.sdp),
            textInputModifier = Modifier.testTag("inputField_test"),
            placeholderText = stringResource(R.string.placeholder_name_pet),
            titleText = stringResource(R.string.pet_name),
            textValue = "",
            onEvent = { },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DropDownBreedPreview() {
    PetJournalTheme {
        DropDown(
            textInputModifier = Modifier,
            textTitleModifier = Modifier.padding(bottom = 4.sdp),
            placeholderText = stringResource(R.string.placeholder_race),
            titleText = stringResource(R.string.race),
            textValue = "",
            onEvent = { },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DropDownSizePreview() {
    PetJournalTheme {
        DropDown(
            textInputModifier = Modifier,
            textTitleModifier = Modifier.padding(bottom = 4.sdp),
            placeholderText = stringResource(R.string.placeholder_size),
            titleText = stringResource(R.string.size),
            textValue = "",
            onEvent = { },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DateInputTextPreview() {
    PetJournalTheme {
        DateInputText(
            textInputModifier = Modifier,
            textTitleModifier = Modifier.padding(bottom = 4.sdp),
            titleText = stringResource(R.string.pet_birth_date),
            placeholderText = stringResource(R.string.placeholder_text_DD_MM_YYYY),
            textValue = "",
            onEvent = { },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DropDownWeightPreview() {
    PetJournalTheme {
        DropDown(
            textTitleModifier = Modifier.padding(bottom = 4.sdp),
            placeholderText = stringResource(id = R.string.placeholder_weight),
            titleText = stringResource(id = R.string.weight),
            textValue = "",
            onEvent = { },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DropDownTypePreview() {
    PetJournalTheme {
        DropDown(
            textInputModifier = Modifier.padding(top = 4.sdp),
            placeholderText = stringResource(R.string.placeholder_type),
            titleText = stringResource(R.string.type),
            textValue = "",
            onEvent = { },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DualActionSexPreview() {
    PetJournalTheme {
        Column {
            Row {
                Text(
                    text = stringResource(R.string.pet_sex),
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colorScheme.scrim,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight(500),
                    modifier =
                        Modifier
                            .padding(bottom = 8.sdp)
                            .fillMaxWidth(),
                )
            }
            DualActionButton(
                buttonModifier = Modifier,
                titleText = stringResource(R.string.pet_sex),
                rightButtonSubmit = {},
                leftButtonSubmit = {},
                enableButton = true,
                leftButtonText = stringResource(R.string.male),
                rightButtonText = stringResource(R.string.female),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DualActionCastratedPreview() {
    PetJournalTheme {
        Column {
            Row {
                Text(
                    text = stringResource(R.string.pet_registration),
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colorScheme.scrim,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight(500),
                    modifier =
                        Modifier
                            .padding(bottom = 8.sdp)
                            .fillMaxWidth(),
                )
            }
            DualActionButton(
                buttonModifier = Modifier,
                titleText = stringResource(R.string.castrated),
                rightButtonSubmit = {},
                leftButtonSubmit = {},
                enableButton = true,
                leftButtonText = stringResource(R.string.yes),
                rightButtonText = stringResource(R.string.no),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Button3SavePreview() {
    PetJournalTheme {
        Button3(
            submit = {},
            enableButton = true,
            text = stringResource(R.string.save),
        )
    }
}

@Composable
private fun DropDown(
    modifier: Modifier = Modifier,
    textTitleModifier: Modifier = Modifier,
    textInputModifier: Modifier = Modifier,
    placeholderText: String = "Porte do seu pet",
    titleText: String = "Title",
    isError: Boolean = false,
    isLoading: Boolean = false,
    textError: List<String>? = null,
    dropdownItems: List<String>? = null,
    onEvent: (String) -> Unit,
    textValue: String,
) {
    var isDropdownExpanded by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        Row {
            Text(
                text = titleText,
                textAlign = TextAlign.Start,
                color = MaterialTheme.colorScheme.scrim,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight(500),
                modifier =
                    textTitleModifier
                        .fillMaxWidth()
                        .padding(end = 24.sdp),
            )
        }
        Row {
            Box(
                modifier =
                    textInputModifier
                        .shadow(
                            elevation = 30.dp,
                            spotColor = ColorCustom.shadow_color,
                            ambientColor = ColorCustom.shadow_color,
                        )
                        .height(50.dp)
                        .background(
                            color = MaterialTheme.colorScheme.surface,
                            shape = RoundedCornerShape(size = 12.dp),
                        )
                        .fillMaxWidth()
                        .drawBehind {
                            if (!isLoading) {
                                val stroke = Stroke(width = 2.dp.toPx())
                                drawRoundRect(
                                    color = if (isError) Color.Transparent else ColorGrid.edge_not_selected,
                                    style = stroke,
                                    cornerRadius = CornerRadius(12.dp.toPx()),
                                )
                            }
                        }
                        .clip(RoundedCornerShape(10.sdp))
                        .clickable(enabled = !isLoading) { isDropdownExpanded = true },
            ) {
                if (isLoading) {
                    Box(
                        modifier =
                            Modifier
                                .fillMaxSize()
                                .shimmerEffect(),
                    )
                } else {
                    Text(
                        modifier =
                            Modifier
                                .padding(start = 14.sdp)
                                .align(Alignment.CenterStart),
                        text = if (isError) "X" else textValue.ifEmpty { placeholderText },
                        style = MaterialTheme.typography.bodyMedium,
                        color =
                            if (isError) {
                                MaterialTheme.colorScheme.error
                            } else if (textValue.isEmpty()) {
                                MaterialTheme.colorScheme.scrim.copy(alpha = 0.3f)
                            } else {
                                MaterialTheme.colorScheme.scrim
                            },
                    )

                    Icon(
                        imageVector = Icons.Rounded.KeyboardArrowDown,
                        contentDescription = "Dropdown",
                        tint = MaterialTheme.colorScheme.outline,
                        modifier =
                            Modifier
                                .padding(end = 10.dp)
                                .align(Alignment.CenterEnd),
                    )

                    DropdownMenu(
                        expanded = isDropdownExpanded,
                        onDismissRequest = { isDropdownExpanded = false },
                        modifier =
                            Modifier
                                .background(MaterialTheme.colorScheme.background)
                                .width(IntrinsicSize.Max)
                                .padding(top = 5.dp)
                                .border(
                                    BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
                                    RoundedCornerShape(10.dp),
                                ),
                    ) {
                        dropdownItems?.forEach { item ->
                            DropdownMenuItem(
                                text = {
                                    Text(text = item)
                                },
                                onClick = {
                                    isDropdownExpanded = false
                                    onEvent(item)
                                },
                                enabled = true,
                                colors =
                                    MenuItemColors(
                                        textColor = MaterialTheme.colorScheme.onSurface,
                                        leadingIconColor = MaterialTheme.colorScheme.onSurface,
                                        trailingIconColor = MaterialTheme.colorScheme.onSurface,
                                        disabledTextColor = MaterialTheme.colorScheme.onSurface,
                                        disabledLeadingIconColor = MaterialTheme.colorScheme.onSurface,
                                        disabledTrailingIconColor = MaterialTheme.colorScheme.onSurface,
                                    ),
                                contentPadding = PaddingValues(horizontal = 10.sdp),
                            )
                        }
                    }
                }
            }
        }

        Row {
            textError?.forEach {
                AlertText(textMessage = it, modifier = Modifier.padding(10.dp))
            }
        }
    }
}

@Composable
private fun DateInputText(
    modifier: Modifier = Modifier,
    textTitleModifier: Modifier = Modifier,
    textInputModifier: Modifier = Modifier,
    placeholderText: String = "Placeholder",
    titleText: String = "Title",
    textValue: String,
    isError: Boolean = false,
    textError: List<String>? = null,
    onEvent: (String) -> Unit,
    visualTransformation: VisualTransformation = VisualTransformation.None,
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val datePickerDialog =
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                val formattedDate = String.format("%02d%02d%04d", dayOfMonth, month + 1, year)
                onEvent(formattedDate)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH),
        ).apply {
            datePicker.maxDate = System.currentTimeMillis()
        }

    val openDialog = { datePickerDialog.show() }

    Column(modifier = modifier) {
        Row {
            Text(
                text = titleText,
                textAlign = TextAlign.Start,
                color = MaterialTheme.colorScheme.scrim,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight(500),
                modifier =
                    textTitleModifier
                        .fillMaxWidth()
                        .padding(end = 24.sdp),
            )
        }
        Row {
            Box(
                modifier =
                    textInputModifier
                        .height(50.dp)
                        .fillMaxWidth()
                        .shadow(
                            elevation = 30.dp,
                            shape = RoundedCornerShape(12.dp),
                            spotColor = ColorCustom.shadow_color,
                            ambientColor = ColorCustom.shadow_color,
                        )
                        .background(
                            color = MaterialTheme.colorScheme.surface,
                            shape = RoundedCornerShape(12.dp),
                        ),
            ) {
                OutlinedTextField(
                    value = textValue,
                    onValueChange = { },
                    readOnly = true,
                    enabled = true,
                    singleLine = true,
                    modifier = Modifier.fillMaxSize(),
                    shape = RoundedCornerShape(12.dp),
                    textStyle =
                        MaterialTheme.typography.bodyMedium.copy(
                            color = if (isSystemInDarkTheme()) ColorCustom.text_style_color else MaterialTheme.colorScheme.onSurface,
                        ),
                    placeholder = {
                        Text(
                            text = placeholderText,
                            style = MaterialTheme.typography.bodySmall,
                        )
                    },
                    visualTransformation = visualTransformation,
                    keyboardOptions =
                        KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number,
                        ),
                    trailingIcon = {
                        val iconModifier =
                            Modifier
                                .padding(10.sdp)
                                .clickable { openDialog() }

                        if (isError) {
                            Icon(
                                painter = painterResource(id = R.drawable.icone_erro),
                                contentDescription = stringResource(R.string.description_error),
                                tint = Color.Unspecified,
                                modifier = iconModifier,
                            )
                        } else if (textValue.length >= 8) {
                            Icon(
                                painter = painterResource(id = R.drawable.icone_verificado_ok),
                                contentDescription = null,
                                tint = Color.Unspecified,
                                modifier = iconModifier,
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.DateRange,
                                contentDescription = "Selecionar Data",
                                tint = MaterialTheme.colorScheme.outline,
                                modifier = iconModifier,
                            )
                        }
                    },
                )

                Box(
                    modifier =
                        Modifier
                            .matchParentSize()
                            .clip(RoundedCornerShape(12.dp))
                            .clickable { openDialog() },
                )
            }
        }

        Row {
            textError?.forEach {
                AlertText(textMessage = it, modifier = Modifier.padding(10.sdp))
            }
        }
    }
}
