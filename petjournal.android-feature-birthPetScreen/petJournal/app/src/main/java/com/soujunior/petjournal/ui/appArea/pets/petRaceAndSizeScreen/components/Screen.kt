package com.soujunior.petjournal.ui.appArea.pets.petRaceAndSizeScreen.components

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.appArea.pets.petRaceAndSizeScreen.RaceSizeFormEvent
import com.soujunior.petjournal.ui.appArea.pets.petRaceAndSizeScreen.ViewModelRaceSize
import com.soujunior.petjournal.ui.components.AutoCompleteDropDown
import com.soujunior.petjournal.ui.components.Breadcrumb
import com.soujunior.petjournal.ui.components.Button3
import com.soujunior.petjournal.ui.components.DashedInputText
import com.soujunior.petjournal.ui.components.DropDown
import com.soujunior.petjournal.ui.components.NavigationBar
import com.soujunior.petjournal.ui.components.ScaffoldCustom
import org.koin.androidx.compose.getViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun Screen(idPetInformation: String?, navController: NavController) {
    val viewModel: ViewModelRaceSize = getViewModel()
    val isTextFiledOthersVisible by viewModel.isTextFiledOthersVisible.collectAsState()
    if (idPetInformation != null) {
        viewModel.getPetInformation(idPetInformation.toLong())
        RaceSizeFormEvent.IdPetInformation(idPetInformation = idPetInformation.toLong())
    }
    Column(modifier = Modifier.navigationBarsPadding()) {
        ScaffoldCustom(
            modifier = Modifier,
            navigationUp = navController,
            showTopBar = true,
            showBottomBarNavigation = true,
            bottomNavigationBar = { NavigationBar(navController) },
            contentToUse = {
                Box(modifier = Modifier.padding(it)) {
                    Image(
                        painter = painterResource(R.drawable.rastro),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.background)
                            .offset(y = 120.dp)
                            .align(Alignment.BottomEnd)
                    )

                    LazyColumn(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 12.dp, end = 12.dp),
                        content = {
                            item {
                                Breadcrumb(index = 2)
                            }
                            item {

                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(5.dp, 0.dp),
                                    contentAlignment = Alignment.TopStart
                                ) {
                                    Header(
                                        petName = viewModel.state.name,
                                        modifier = Modifier.padding(5.dp, 0.dp)
                                    )

                                }

                            }


                            item {
                                DropDown(
                                    modifier = Modifier,
                                    textInputModifier = Modifier,
                                    placeholderText = "Porte do seu pet",
                                    textValue = viewModel.state.size,
                                    textError = viewModel.state.sizeError,
                                    isError = !viewModel.state.sizeError.isNullOrEmpty(),
                                    titleText = "Porte: ",
                                    dropdownItems = viewModel.sizeList.value,
                                    onEvent = { it: String ->
                                        viewModel.onEvent(
                                            RaceSizeFormEvent.PetSize(it)
                                        )
                                    }
                                )
                            }

                            item {
                                AutoCompleteDropDown(
                                    modifier = Modifier,
                                    placeholderText = "Raça do seu pet",
                                    textValue = viewModel.state.race,
                                    textError = viewModel.state.raceError,
                                    isError = !viewModel.state.raceError.isNullOrEmpty(),
                                    titleText = "Raça: ",
                                    dropdownItems = viewModel.raceList.value,
                                    onEvent = { it: String ->
                                        viewModel.onEvent(
                                            RaceSizeFormEvent.PetRace(it)
                                        )
                                    }
                                )
                            }

                            if (viewModel.enableRaceOthers()) {
                                item {
                                    DashedInputText(
                                        modifier = Modifier,
                                        textInputModifier = Modifier.height(50.dp),
                                        placeholderText = "Digite aqui...",
                                        textValue = viewModel.state.raceOthers,
                                        textError = viewModel.state.raceOthersError,
                                        isError = !viewModel.state.raceOthersError.isNullOrEmpty(),
                                        titleText = "Raça do seu pet",
                                        onEvent = { it: String ->
                                            viewModel.onEvent(
                                                RaceSizeFormEvent.PetRaceOthers(it)
                                            )
                                        }
                                    )
                                }
                            }

                            item {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .width(250.dp)
                                        .height(250.dp)
                                        .offset(y = (-60).dp)
                                ) {
                                    Image(
                                        painter = painterResource(R.drawable.pet_heart),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .background(Color.Transparent)
                                    )
                                }
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .align(Alignment.Center)
                                        .padding(start = 30.dp)
                                        .offset(y = (-60).dp)
                                ) {


                                    Button3(
                                        submit = { navController.popBackStack() },
                                        enableButton = true,
                                        modifier = Modifier
                                            .width(150.dp)
                                            .height(50.dp),
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

                                            if (isTextFiledOthersVisible) {
                                                if (viewModel.enableButton() &&
                                                    viewModel.state.raceOthers.isNotEmpty() &&
                                                    viewModel.state.size.isNotEmpty()

                                                ) {
                                                    viewModel.updatePetInformation()
                                                    navController.navigate("pets/birth/$idPetInformation")
                                                }
                                            } else {
                                                if (viewModel.enableButton() &&
                                                    viewModel.state.race.isNotEmpty() &&
                                                    viewModel.state.size.isNotEmpty() &&
                                                    viewModel.state.race.lowercase() != "outro"
                                                ) {
                                                    viewModel.updatePetInformation()
                                                    navController.navigate("pets/birth/$idPetInformation")
                                                }
                                            }


                                        },
                                        enableButton = viewModel.enableButton(),
                                        modifier = Modifier
                                            .width(150.dp)
                                            .height(50.dp),
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

            })

    }

}