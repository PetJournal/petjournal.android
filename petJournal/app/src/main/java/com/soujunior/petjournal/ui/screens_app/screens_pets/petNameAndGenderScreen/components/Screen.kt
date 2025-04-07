package com.soujunior.petjournal.ui.screens_app.screens_pets.petNameAndGenderScreen.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.components.Button2
import com.soujunior.petjournal.ui.components.Button3
import com.soujunior.petjournal.ui.components.DashedInputText
import com.soujunior.petjournal.ui.components.IndeterminateCircularIndicator
import com.soujunior.petjournal.ui.components.NavigationBar
import com.soujunior.petjournal.ui.components.ScaffoldCustom
import com.soujunior.petjournal.ui.screens_app.screens_pets.petNameAndGenderScreen.FakeNameAndGenderViewModel
import com.soujunior.petjournal.ui.screens_app.screens_pets.petNameAndGenderScreen.NameGenderFormEvent
import com.soujunior.petjournal.ui.screens_app.screens_pets.petNameAndGenderScreen.ViewModelNameGender
import com.soujunior.petjournal.ui.states.TaskState
import com.soujunior.petjournal.ui.theme.PetJournalTheme
import com.soujunior.petjournal.ui.util.Constants.BIRD
import com.soujunior.petjournal.ui.util.Constants.CAT
import com.soujunior.petjournal.ui.util.Constants.DOG
import com.soujunior.petjournal.ui.util.Constants.FISH
import com.soujunior.petjournal.ui.util.Constants.REPTILE
import com.soujunior.petjournal.ui.util.Constants.RODENT
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp
import org.koin.androidx.compose.getViewModel

@Composable
fun getNameAndGenderViewModelForPreview(): ViewModelNameGender {
    return if (LocalInspectionMode.current) {
        FakeNameAndGenderViewModel()
    } else {
        getViewModel()
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun Screen(idPetInformation: String?, navController: NavController) {
    val viewModel: ViewModelNameGender = getNameAndGenderViewModelForPreview()
    val taskState by viewModel.taskState.collectAsState()
    var isClearGender by remember { mutableStateOf(false) }
    if (idPetInformation != null) {
        viewModel.getPetInformation(idPetInformation.toLong())
    }
    val isDarkMode = isSystemInDarkTheme()
    val specieName: Int = when (viewModel.state.specie) {
        DOG -> R.string.dog
        CAT -> R.string.cat
        BIRD -> R.string.bird
        FISH -> R.string.fish
        REPTILE -> R.string.reptile
        RODENT -> R.string.rodent
        else -> R.string.other
    }
    Column(modifier = Modifier.navigationBarsPadding()) {
        ScaffoldCustom(
            modifier = Modifier,
            navigationUp = navController,
            showTopBar = true,
            showBottomBarNavigation = true,
            bottomNavigationBar = { NavigationBar(navController) },
            contentToUse = {
                    if (taskState is TaskState.Loading)
                        IndeterminateCircularIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                    else {
                        Column(
                            modifier = Modifier
                                .fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Top,
                        ) {
                            Header(
                                species = if (specieName == R.string.other) viewModel.state.specie else stringResource(id = specieName)
                            )
                            Spacer(modifier = Modifier.padding(12.sdp))

                            LazyColumn(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Top,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight()
                                    .padding(
                                        start = 16.sdp,
                                        end = 16.sdp,
                                        bottom = it.calculateBottomPadding() + 1.sdp
                                    )
                                    .background(MaterialTheme.colorScheme.background),
                                content = {
                                    item {
                                        Text(
                                            modifier = Modifier
                                                .fillMaxWidth(),
                                            text = stringResource(id = R.string.pet_name),
                                            style = MaterialTheme.typography.bodySmall,
                                            textAlign = TextAlign.Center,
                                            fontSize = 14.ssp,
                                            fontWeight = FontWeight.W400
                                        )
                                    }
                                    item {
                                        DashedInputText(
                                            modifier = Modifier,
                                            textInputModifier = Modifier.height(45.sdp),
                                            placeholderText = stringResource(R.string.placeholder_name_pet),
                                            textValue = viewModel.state.name,
                                            textError = viewModel.state.nameError,
                                            isError = !viewModel.state.nameError.isNullOrEmpty(),
                                            titleText = stringResource(id = R.string.name_pet),
                                            onEvent = { it: String ->
                                                viewModel.onEvent(
                                                    NameGenderFormEvent.PetName(it)
                                                )
                                            }
                                        )
                                    }
                                    item {
                                        GenderSelector(
                                            selectedGender = { selectedGender ->
                                                if (selectedGender.isNotEmpty()) {
                                                    isClearGender = false
                                                }
                                                viewModel.onEvent(
                                                    NameGenderFormEvent.PetGender(
                                                        selectedGender
                                                    )
                                                )
                                            },
                                            clearSelection = {
                                                isClearGender
                                            },
                                            textError = viewModel.state.genderError
                                        )
                                    }
                                    item {
                                        Spacer(modifier = Modifier.height(15.sdp))
                                        Row(
                                            verticalAlignment = Alignment.Bottom
                                        ) {
                                            Button3(
                                                submit = { navController.popBackStack() },
                                                enableButton = true,
                                                modifier = Modifier
                                                    .weight(1f)
                                                    .padding(end = 5.sdp),
                                                text = stringResource(R.string.back),
                                                buttonColor = ButtonDefaults.buttonColors(
                                                    MaterialTheme.colorScheme.surface
                                                ),
                                                textColor = MaterialTheme.colorScheme.primary
                                            )
                                            Spacer(modifier = Modifier.width(10.sdp))
                                            Button2(
                                                submit = {
                                                    viewModel.onEvent(
                                                        NameGenderFormEvent.NextButton
                                                    )

                                                    if (viewModel.enableButton() &&
                                                        viewModel.state.name.isNotEmpty() &&
                                                        viewModel.state.gender.isNotEmpty()
                                                    ) {
                                                        viewModel.updatePetInformation()
                                                        navController.navigate("pets/raceAndSize/$idPetInformation")
                                                    }
                                                },
                                                enableButton = viewModel.enableButton(),
                                                modifier = Modifier
                                                    .weight(1f)
                                                    .padding(start = 5.sdp),
                                                isLoading = taskState is TaskState.Loading,
                                                text = stringResource(R.string.text_continue),
                                                buttonColor = if (isDarkMode)  ButtonDefaults.buttonColors(MaterialTheme.colorScheme.onSecondary)
                                                else ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
                                                textColor = if (isDarkMode) MaterialTheme.colorScheme.surface else Color.White,
                                            )
                                        }
                                    }
                                })
                        }
                    }

            })
    }
}

@Preview(showBackground = true)
@Composable
fun NameAndGenderPreview() {
    val nav = rememberNavController()
    PetJournalTheme {
        Screen("1", nav)
    }
}