package com.soujunior.petjournal.ui.appArea.pets.petBirthDateScreen.components

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.appArea.pets.petBirthDateScreen.BirthDateFormEvent
import com.soujunior.petjournal.ui.appArea.pets.petBirthDateScreen.ViewModelBirthDate
import com.soujunior.petjournal.ui.appArea.pets.petRaceAndSizeScreen.RaceSizeFormEvent
import com.soujunior.petjournal.ui.components.Breadcrumb
import com.soujunior.petjournal.ui.components.Button3
import com.soujunior.petjournal.ui.components.DateInputText
import com.soujunior.petjournal.ui.components.NavigationBar
import com.soujunior.petjournal.ui.components.ScaffoldCustom
import com.soujunior.petjournal.ui.components.mask.formatDate
import org.koin.androidx.compose.getViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun Screen(idPetInformation: String?, navController: NavController) {
    val viewModel: ViewModelBirthDate = getViewModel()
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
                Box(modifier = Modifier.padding(it)) {
                    LazyColumn(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 12.dp, end = 12.dp),
                        content = {
                            item {
                                Breadcrumb(index = 3)
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
                                        modifier = Modifier.padding(5.dp, 0.dp),
                                        petGender = if (viewModel.state.gender.uppercase() == "M") "Adotado" else "Adotada"
                                    )
                                }

                            }
                            item {
                                DateInputText(
                                    titleText = "Data de nascimento",
                                    placeholderText = "DD/MM/AAAA",
                                    textValue = viewModel.state.birth,
                                    textError = viewModel.state.birthError,
                                    isError = !viewModel.state.birthError.isNullOrEmpty(),
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    onEvent = { value: String ->
                                        viewModel.onEvent(BirthDateFormEvent.PetBirthDate(value))
                                    },
                                    visualTransformation = { formatDate(it) }
                                )
                            }
                            item {
                                Row(
                                    Modifier.padding(top = 80.dp)
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
                                                BirthDateFormEvent.NextButton
                                            )

                                            if (viewModel.enableButton() &&
                                                viewModel.state.birth.isNotEmpty()
                                            ) {
                                                Log.i(TAG, viewModel.state.birth)
                                                viewModel.updatePetInformation()
                                                //navController.navigate("pets/birth/$it")
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