package com.soujunior.petjournal.ui.screens_app.screens_pets.speciesChoiceScreen.components

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.components.Button2
import com.soujunior.petjournal.ui.components.Button3
import com.soujunior.petjournal.ui.components.IndeterminateCircularIndicator
import com.soujunior.petjournal.ui.components.InputSpecies
import com.soujunior.petjournal.ui.components.NavigationBar
import com.soujunior.petjournal.ui.components.ScaffoldCustom
import com.soujunior.petjournal.ui.screens_app.screens_pets.speciesChoiceScreen.FakeChoiceSpeciesViewModel
import com.soujunior.petjournal.ui.screens_app.screens_pets.speciesChoiceScreen.PetFormEvent
import com.soujunior.petjournal.ui.screens_app.screens_pets.speciesChoiceScreen.ViewModelChoiceSpecies
import com.soujunior.petjournal.ui.states.TaskState
import com.soujunior.petjournal.ui.theme.PetJournalTheme
import com.soujunior.petjournal.ui.util.Constants.RACE_OTHER
import com.soujunior.petjournal.ui.util.ValidationEvent
import ir.kaaveh.sdpcompose.sdp
import org.koin.androidx.compose.getViewModel

@Composable
fun getSpecieChoiceViewModelForPreview(): ViewModelChoiceSpecies {
    return if (LocalInspectionMode.current) {
        FakeChoiceSpeciesViewModel()
    } else {
        getViewModel()
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun Screen(navController: NavController) {
    val viewModel: ViewModelChoiceSpecies = getSpecieChoiceViewModelForPreview()
    val activateContinueButton = remember { mutableStateOf(false) }
    val taskState by viewModel.taskState.collectAsState()
    var isOthersFieldVisible by remember { mutableStateOf(false) }
    var isClearSpecies by remember { mutableStateOf(false) }
    var speciesName: String? = null
    val context = LocalContext.current
    val isDarkMode = isSystemInDarkTheme()
    LaunchedEffect(context) {
        viewModel.validationEvents.collect { event ->
            when (event) {
                is ValidationEvent.Success -> {
                    navController.navigate("pets/nameAndGender/${viewModel.state.idRoomPetInformation}")
                }

                is ValidationEvent.Failed -> {
                    Log.i(TAG, viewModel.state.idRoomPetInformation.toString())
                }
            }
        }
    }


    Column(modifier = Modifier.navigationBarsPadding()) {
        ScaffoldCustom(
            modifier = Modifier,
            titleTopBar = stringResource(id = R.string.pet_registration),
            titleTopBarAligh = Alignment.Center,
            navigationUp = navController,
            showTopBar = true,
            showBottomBarNavigation = true,
            bottomNavigationBar = { NavigationBar(navController) },
            contentToUse = {
                if (taskState is TaskState.Idle) {
                    IndeterminateCircularIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top,
                    )
                    {

                        Header(name = viewModel.state.name ?: "")
                        LazyColumn(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Top,
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .padding(
                                    start = 16.sdp,
                                    end = 16.sdp,
                                    top = 25.sdp,
                                    bottom = it.calculateBottomPadding() + 15.sdp
                                )
                                .background(MaterialTheme.colorScheme.background),
                            content = {
                                item {

                                    GridVectors(
                                        selectedSpecies = { selectedSpecies ->
                                            if (selectedSpecies.isNotEmpty() && selectedSpecies != RACE_OTHER) {
                                                activateContinueButton.value = true
                                                isOthersFieldVisible = false
                                                isClearSpecies = false
                                                speciesName = selectedSpecies
                                            }else if (selectedSpecies == RACE_OTHER){
                                                isOthersFieldVisible = true
                                                isClearSpecies = false
                                            }
                                        },
                                        clearSelection = {
                                            isClearSpecies
                                        }
                                    )
                                }
                                item {

                                    if (isOthersFieldVisible) {
                                        Spacer(modifier = Modifier.padding(10.sdp))
                                        InputSpecies(
                                            textTop = stringResource(id = R.string.insert_the_species),
                                            textHint = stringResource(R.string.insert_the_species),
                                            textValue = viewModel.state.specie,
                                            textError = viewModel.state.specieError,
                                            onEvent = { value ->
                                                viewModel.onEvent(PetFormEvent.OtherSpecie(value))
                                                activateContinueButton.value =
                                                    viewModel.enableButton()
                                                speciesName = value
                                            })
                                    }
                                    Spacer(modifier = Modifier.padding(10.sdp))
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(MaterialTheme.colorScheme.background)
                                    ) {
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
                                                buttonColor = if (isDarkMode) ButtonDefaults.buttonColors(MaterialTheme.colorScheme.onPrimary)
                                                else ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
                                                text = stringResource(R.string.text_continue),
                                                border = null,
                                                textColor = if (isDarkMode) MaterialTheme.colorScheme.primary else Color.White,
                                                submit = {
                                                    speciesName?.let { specie ->
                                                        viewModel.savePetInformation(specie)
                                                    }
                                                },
                                                enableButton = viewModel.enableButton(),
                                                modifier = Modifier
                                                    .weight(1f)
                                                    .padding(start = 5.sdp),
                                                isLoading = taskState is TaskState.Loading
                                            )

                                        }
                                    }
                                }
                            }
                        )
                    }
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SpecieChoicePreview() {
    val nav = rememberNavController()
    PetJournalTheme {
        Screen(nav)
    }
}