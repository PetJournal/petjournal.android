package com.soujunior.petjournal.ui.appArea.pets.petBirthScreen.components

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.appArea.pets.petNameAndGenderScreen.NameGenderFormEvent
import com.soujunior.petjournal.ui.appArea.pets.petNameAndGenderScreen.ViewModelNameGender
import com.soujunior.petjournal.ui.components.Breadcrumb
import com.soujunior.petjournal.ui.components.Button3
import com.soujunior.petjournal.ui.components.DashedInputText
import com.soujunior.petjournal.ui.components.NavigationBar
import com.soujunior.petjournal.ui.components.ScaffoldCustom
import org.koin.androidx.compose.getViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun Screen(petSpecie: String?, navController: NavController){
    val viewModel : ViewModelNameGender = getViewModel()
    val taskState = viewModel.taskState.collectAsState()
    var isClearGender by remember { mutableStateOf(false) }

    Column(modifier = Modifier.navigationBarsPadding()){
        ScaffoldCustom(
            modifier = Modifier,
            navigationUp = navController,
            showTopBar = true,
            showBottomBarNavigation = true,
            bottomNavigationBar = { NavigationBar(navController) },
            contentToUse = {
                Box(modifier = Modifier.padding(it)){
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
                                    Header(
                                        petName = petSpecie ?: "ERRO",
                                        modifier = Modifier.padding(5.dp, 0.dp)
                                    )
                                }
                                item {
                                    Text(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp, 8.dp),
                                        text = stringResource(id = R.string.pet_name),
                                        style = MaterialTheme.typography.bodyMedium,
                                        textAlign = TextAlign.Center,
                                        color = MaterialTheme.colorScheme.primary,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Normal
                                    )
                                }
                                item {
                                    DashedInputText(
                                        modifier = Modifier,
                                        textInputModifier = Modifier,
                                        placeholderText = "Digite aqui...",
                                        textValue = viewModel.state.name,
                                        textError = viewModel.state.nameError,
                                        isError = !viewModel.state.nameError.isNullOrEmpty(),
                                        titleText = "Nome: ",
                                        onEvent = { it: String ->
                                            viewModel.onEvent(
                                                NameGenderFormEvent.PetName(it)
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
                                                    NameGenderFormEvent.NextButton
                                                )

                                                if (viewModel.enableButton() &&
                                                    viewModel.state.name.isNotEmpty() &&
                                                    viewModel.state.gender.isNotEmpty()
                                                ) {

                                                    viewModel.state.name.let {
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