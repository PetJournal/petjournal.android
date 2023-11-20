package com.soujunior.petjournal.ui.appArea.pets.speciesChoiceScreen.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
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
import com.soujunior.petjournal.ui.components.InputSpecies
import com.soujunior.petjournal.ui.components.NavigationBar
import com.soujunior.petjournal.ui.components.ScaffoldCustom
import org.koin.androidx.compose.getViewModel

@Composable
fun Screen(navController: NavController, name: String) {
    val viewModel: ViewModelChoiceSpecies = getViewModel()
    val activateContinueButton = remember { mutableStateOf(false) }
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
                    LazyColumn(
                        verticalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        item {
                            Header(name = name)
                        }
                        item {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(MaterialTheme.colorScheme.background)
                            ) {
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
                            }
                        }
                        item {
                            Spacer(modifier = Modifier.height(20.dp))
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Button2(
                                    submit = {
                                        isOthersFieldVisible = true
                                        isClearSpecies = true
                                    },
                                    modifier = Modifier.width(180.dp),
                                    enableButton = true,
                                    text = stringResource(R.string.others),
                                )
                            }
                        }
                        item {
                            if (isOthersFieldVisible) {
                                Spacer(modifier = Modifier.height(20.dp))
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
                        }
                        item {
                            Spacer(modifier = Modifier.height(20.dp))
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
                                        text = stringResource(R.string.text_continue)
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