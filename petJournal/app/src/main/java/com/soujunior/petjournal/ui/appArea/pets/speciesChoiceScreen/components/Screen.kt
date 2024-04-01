package com.soujunior.petjournal.ui.appArea.pets.speciesChoiceScreen.components

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavController
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.appArea.pets.speciesChoiceScreen.PetFormEvent
import com.soujunior.petjournal.ui.appArea.pets.speciesChoiceScreen.ViewModelChoiceSpecies
import com.soujunior.petjournal.ui.components.Button2
import com.soujunior.petjournal.ui.components.IndeterminateCircularIndicator
import com.soujunior.petjournal.ui.components.InputSpecies
import com.soujunior.petjournal.ui.components.NavigationBar
import com.soujunior.petjournal.ui.components.ScaffoldCustom
import com.soujunior.petjournal.ui.states.TaskState
import org.koin.androidx.compose.getViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun Screen(navController: NavController) {
    val viewModel: ViewModelChoiceSpecies = getViewModel()
    val activateContinueButton = remember { mutableStateOf(false) }
    val name = remember { viewModel.name }
    val taskState by viewModel.taskState.collectAsState()
    var isOthersFieldVisible by remember { mutableStateOf(false) }
    var isClearSpecies by remember { mutableStateOf(false) }
    var speciesName: String? = null
    val idRoomPetInfoState by viewModel.idRoomPetInformation.collectAsState()


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
                Box(modifier = Modifier.padding(it)) {
                    if (taskState is TaskState.Loading)
                        IndeterminateCircularIndicator(modifier = Modifier.align(Alignment.Center))
                    else {
                        LazyColumn(
                            modifier = Modifier
                                .background(MaterialTheme.colorScheme.background)
                                .fillMaxSize()
                                .padding(start = 12.dp, end = 12.dp)
                        ) {
                            item {
                                Header(name = name.value)

                                GridVectors(
                                    selectedSpecies = { selectedSpecies ->
                                        if (selectedSpecies.isNotEmpty()) {
                                            activateContinueButton.value = true
                                            isOthersFieldVisible = false
                                            isClearSpecies = false
                                            speciesName = selectedSpecies
                                        }
                                    },
                                    clearSelection = {
                                        isClearSpecies
                                    }
                                )

                                Row(
                                    horizontalArrangement = Arrangement.Center,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Button2(
                                        submit = {
                                            isOthersFieldVisible = true
                                            isClearSpecies = true
                                        },
                                        enableButton = true,
                                        text = stringResource(R.string.others),
                                        buttonColor =
                                        ButtonDefaults.buttonColors(MaterialTheme.colorScheme.inverseSurface),
                                        textColor = Color.White
                                    )
                                }

                                if (isOthersFieldVisible) {
                                    Spacer(modifier = Modifier.padding(16.dp))
                                    InputSpecies(
                                        textTop = stringResource(id = R.string.insert_the_species),
                                        textHint = stringResource(R.string.insert_the_species),
                                        textValue = viewModel.state.specie,
                                        textError = viewModel.state.specieError,
                                        onEvent = { value ->
                                            viewModel.onEvent(PetFormEvent.OtherSpecie(value))
                                            activateContinueButton.value = viewModel.enableButton()
                                            speciesName = value
                                        })
                                }
                                Spacer(modifier = Modifier.padding(16.dp))
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(MaterialTheme.colorScheme.background)
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.Bottom
                                    ) {
                                        Button2(
                                            submit = {
                                                navController.popBackStack()
                                            },
                                            modifier = Modifier.width(150.dp),
                                            enableButton = true,
                                            border = BorderStroke(
                                                width = 2.dp,
                                                color = MaterialTheme.colorScheme.primary
                                            ),
                                            text = stringResource(R.string.back),
                                            buttonColor = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.surface),
                                            textColor = MaterialTheme.colorScheme.primary
                                        )
                                        Spacer(modifier = Modifier.width(16.dp))
                                        Button2(
                                            submit = {
                                                speciesName?.let {
                                                viewModel.savePetInformation(it)
                                                    //navController.navigate("pets/nameAndGender/${viewModel.idRoomPetInformation}")
                                                    Log.i("tela", idRoomPetInfoState.toString())
                                                }
                                            },
                                            modifier = Modifier.width(150.dp),
                                            enableButton = activateContinueButton.value,
                                            border = BorderStroke(
                                                width = 2.dp,
                                                color =
                                                if (activateContinueButton.value) MaterialTheme.colorScheme.primary
                                                else MaterialTheme.colorScheme.outline
                                            ),
                                            text = stringResource(R.string.text_continue),
                                            buttonColor =
                                            ButtonDefaults.buttonColors(
                                                if (activateContinueButton.value) MaterialTheme.colorScheme.primary
                                                else MaterialTheme.colorScheme.surface
                                            ),
                                            textColor = if (activateContinueButton.value) MaterialTheme.colorScheme.surface
                                            else MaterialTheme.colorScheme.outline
                                        )

                                    }
                                }
                            }
                        }
                    }
                }
            }
        )
    }
}