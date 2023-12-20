package com.soujunior.petjournal.ui.appArea.pets.speciesChoiceScreen.components

import android.annotation.SuppressLint
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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
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
                        Column(
                            modifier = Modifier
                                .background(MaterialTheme.colorScheme.background)
                                .fillMaxSize()
                                .padding(12.dp)
                        ) {
                            Header(name = name.value)

                            GridVectors(
                                selectedSpecies = { selectedSpecies ->
                                    if (selectedSpecies.isNotEmpty()) {
                                        activateContinueButton.value = true
                                        isOthersFieldVisible = false
                                        isClearSpecies = false
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
                                    textColor =
                                    if (!isOthersFieldVisible) MaterialTheme.colorScheme.onPrimary
                                    else MaterialTheme.colorScheme.scrim
                                )
                            }

                            if (isOthersFieldVisible) {
                                Spacer(modifier = Modifier.weight(4f))
                                InputSpecies(
                                    textTop = stringResource(id = R.string.insert_the_species),
                                    textHint = stringResource(R.string.insert_the_species),
                                    textValue = viewModel.state.specie,
                                    textError = viewModel.state.specieError,
                                    onEvent = { value ->
                                        viewModel.onEvent(PetFormEvent.OtherSpecie(value))
                                        activateContinueButton.value = viewModel.enableButton()
                                    })
                            }
                            Spacer(modifier = Modifier.weight(4f))
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
                                        submit = { /*TODO*/ },
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
        )
    }
}