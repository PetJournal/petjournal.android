package com.soujunior.petjournal.ui.appArea.pets.petRaceAndSizeScreen.components

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.appArea.pets.petNameAndGenderScreen.NameGenderFormEvent
import com.soujunior.petjournal.ui.appArea.pets.petRaceAndSizeScreen.RaceSizeFormEvent
import com.soujunior.petjournal.ui.appArea.pets.petRaceAndSizeScreen.ViewModelRaceSize
import com.soujunior.petjournal.ui.components.Breadcrumb
import com.soujunior.petjournal.ui.components.Button3
import com.soujunior.petjournal.ui.components.DashedInputText
import com.soujunior.petjournal.ui.components.DropDownInputText
import com.soujunior.petjournal.ui.components.NavigationBar
import com.soujunior.petjournal.ui.components.ScaffoldCustom
import org.koin.androidx.compose.getViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun Screen(petName: String?, navController: NavController) {
    val viewModel: ViewModelRaceSize = getViewModel()
    val taskState = viewModel.taskState.collectAsState()
    var isClearGender by remember { mutableStateOf(false) }

    Column(modifier = Modifier.navigationBarsPadding()) {
        ScaffoldCustom(
            modifier = Modifier,
            navigationUp = navController,
            showTopBar = true,
            showBottomBarNavigation = true,
            bottomNavigationBar = { NavigationBar(navController) },
            contentToUse = {
                Box(modifier = Modifier.padding(it)) {
//                    if (taskState is TaskState.Loading)
//                        IndeterminateCircularIndicator(modifier = Modifier.align(Alignment.Center))
//                    else {
                    LazyColumn(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 12.dp, end = 12.dp),
                        content = {
                            item {
                                Breadcrumb(index = 1)
                            }
                            item {

                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(5.dp, 0.dp),
                                    contentAlignment = Alignment.TopStart
                                ) {
                                    Spacer(modifier = Modifier.width(16.dp))
                                    Header(
                                        species = petName ?: "ERRO",
                                        modifier = Modifier.padding(5.dp, 0.dp)
                                    )
                                    Spacer(modifier = Modifier.width(16.dp))
                                }

                            }


                            item {
                                DashedInputText(
                                    modifier = Modifier,
                                    textInputModifier = Modifier,
                                    placeholderText = "Porte do seu pet",
                                    textValue = viewModel.state.size,
                                    textError = viewModel.state.sizeError,
                                    isError = !viewModel.state.sizeError.isNullOrEmpty(),
                                    titleText = "Porte: ",
                                    onEvent = { it: String ->
                                        viewModel.onEvent(
                                            RaceSizeFormEvent.PetSize(it)
                                        )
                                    }
                                )
                            }
                            item {
                                DashedInputText(
                                    modifier = Modifier,
                                    textInputModifier = Modifier,
                                    placeholderText = "Raça do seu pet",
                                    textValue = viewModel.state.race,
                                    textError = viewModel.state.raceError,
                                    isError = !viewModel.state.raceError.isNullOrEmpty(),
                                    titleText = "Raça: ",
                                    onEvent = { it: String ->
                                        viewModel.onEvent(
                                            RaceSizeFormEvent.PetRace(it)
                                        )
                                    }
                                )
                            }
                            item {

                                Row {
                                    Button3(
                                        submit = { navController.popBackStack() },
                                        enableButton = true,
                                        modifier = Modifier.width(150.dp),
                                        border = BorderStroke(
                                            width = 2.dp,
                                            color = MaterialTheme.colorScheme.primary
                                        ),
                                        text = stringResource(R.string.back),
                                        buttonColor = ButtonDefaults.buttonColors(
                                            MaterialTheme.colorScheme.surface
                                        ),
                                        textColor = MaterialTheme.colorScheme.primary
                                    )
                                    Spacer(modifier = Modifier.width(16.dp))
                                    Button3(
                                        submit = {
                                            viewModel.onEvent(
                                                RaceSizeFormEvent.NextButton
                                            )

                                            if (viewModel.enableButton() &&
                                                viewModel.state.race.isNotEmpty() &&
                                                viewModel.state.size.isNotEmpty()
                                            ) {

                                                viewModel.state.race.let {
                                                    navController.navigate("pets/raceAndSize/$it")
                                                }
                                            }
                                        },
                                        enableButton = viewModel.enableButton(),
                                        modifier = Modifier.width(150.dp),
                                        border = BorderStroke(
                                            width = 2.dp,
                                            color =
                                            MaterialTheme.colorScheme.primary
                                        ),
                                        text = stringResource(R.string.text_continue),
                                        buttonColor = ButtonDefaults.buttonColors(
                                            MaterialTheme.colorScheme.primary
                                        ),
                                        textColor = MaterialTheme.colorScheme.surface,
                                    )
                                }
                            }
                        })
                }

//                    }
            })

    }

}