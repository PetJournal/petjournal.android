package com.soujunior.petjournal.ui.screensapp.screenspets.petBirthDateScreen.components

import android.annotation.SuppressLint
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.components.Button3
import com.soujunior.petjournal.ui.components.DateInputText
import com.soujunior.petjournal.ui.components.IndeterminateCircularIndicator
import com.soujunior.petjournal.ui.components.NavigationBar
import com.soujunior.petjournal.ui.components.ScaffoldCustom
import com.soujunior.petjournal.ui.components.TrailBack
import com.soujunior.petjournal.ui.components.mask.formatDate
import com.soujunior.petjournal.ui.screensapp.screenspets.petBirthDateScreen.BirthDateFormEvent
import com.soujunior.petjournal.ui.screensapp.screenspets.petBirthDateScreen.BirthDateViewModel
import com.soujunior.petjournal.ui.screensapp.screenspets.petBirthDateScreen.FakeBirthDateViewModel
import com.soujunior.petjournal.ui.screensapp.screenspets.petRaceAndSizeScreen.RaceSizeFormEvent
import com.soujunior.petjournal.ui.states.TaskState
import com.soujunior.petjournal.ui.theme.PetJournalTheme
import ir.kaaveh.sdpcompose.sdp
import org.koin.androidx.compose.getViewModel

@Composable
fun getBirthDateViewModelForPreview(): BirthDateViewModel {
    return if (LocalInspectionMode.current) {
        FakeBirthDateViewModel()
    } else {
        getViewModel()
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun Screen(
    idPetInformation: String?,
    navController: NavController,
) {
    val viewModel: BirthDateViewModel = getBirthDateViewModelForPreview()
    val taskState by viewModel.taskState.collectAsState()
    var isClearCastration by remember { mutableStateOf(false) }
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
            contentToUse = { it ->
                if (taskState is TaskState.Loading) {
                    IndeterminateCircularIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                } else {
                    TrailBack()
                    Column(
                        modifier =
                            Modifier
                                .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top,
                    ) {
                        Header(
                            petName = viewModel.state.name,
                            petGender =
                                if (viewModel.state.gender.uppercase() ==
                                    stringResource(
                                        R.string.pet_gender_letter_M,
                                    )
                                ) {
                                    stringResource(R.string.pet_he_adopted)
                                } else {
                                    stringResource(R.string.pet_she_adopted)
                                },
                        )
                        Spacer(modifier = Modifier.padding(12.sdp))

                        LazyColumn(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Top,
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight()
                                    .padding(
                                        start = 16.sdp,
                                        end = 16.sdp,
                                        bottom = it.calculateBottomPadding() + 1.sdp,
                                    ),
                            content = {
                                item {
                                    DateInputText(
                                        titleText = stringResource(R.string.pet_birth_date),
                                        placeholderText = stringResource(R.string.placeholder_text_DD_MM_YYYY),
                                        textValue = viewModel.state.birth,
                                        textError = viewModel.state.birthError,
                                        isError = !viewModel.state.birthError.isNullOrEmpty(),
                                        modifier =
                                            Modifier
                                                .fillMaxWidth(),
                                        onEvent = { value: String ->
                                            viewModel.onEvent(BirthDateFormEvent.PetBirthDate(value))
                                        },
                                        visualTransformation = { formatDate(it) },
                                    )
                                }
                                item {
                                    CastrationSelector(
                                        textNamePet = viewModel.state.name,
                                        selectedCastration = { selectedCastration ->
                                            if (selectedCastration != null) {
                                                isClearCastration = false
                                            }
                                            viewModel.onEvent(
                                                BirthDateFormEvent.PetCastration(
                                                    selectedCastration,
                                                ),
                                            )
                                        },
                                        clearSelection = {
                                            isClearCastration
                                        },
                                        textError = viewModel.state.castrationError,
                                    )
                                }
                                item {
                                    Spacer(modifier = Modifier.height(25.sdp))
                                    Row(
                                        verticalAlignment = Alignment.Bottom,
                                    ) {
                                        Button3(
                                            submit = { navController.popBackStack() },
                                            enableButton = true,
                                            modifier =
                                                Modifier
                                                    .weight(1f)
                                                    .padding(end = 5.sdp),
                                            text = stringResource(R.string.back),
                                            buttonColor =
                                                ButtonDefaults.buttonColors(
                                                    MaterialTheme.colorScheme.surface,
                                                ),
                                            textColor = MaterialTheme.colorScheme.primary,
                                        )
                                        Spacer(modifier = Modifier.width(10.sdp))
                                        Button3(
                                            submit = {
                                                viewModel.onEvent(
                                                    BirthDateFormEvent.NextButton,
                                                )

                                                if (viewModel.enableButton() &&
                                                    viewModel.state.birth.isNotEmpty() &&
                                                    viewModel.state.castration != null
                                                ) {
                                                    viewModel.updatePetInformation()
                                                    viewModel.createPetInformation()
                                                    // navController.navigate("pets/birth/$it")
                                                }
                                            },
                                            enableButton = viewModel.enableButton(),
                                            modifier =
                                                Modifier
                                                    .weight(1f)
                                                    .padding(start = 5.sdp),
                                            text = stringResource(R.string.text_continue),
                                            buttonColor =
                                                ButtonDefaults.buttonColors(
                                                    MaterialTheme.colorScheme.primary,
                                                ),
                                            textColor = MaterialTheme.colorScheme.surface,
                                        )
                                    }
                                }
                            },
                        )
                    }
                }
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BirthDatePreview() {
    val nav = rememberNavController()
    PetJournalTheme {
        Screen("1", nav)
    }
}
