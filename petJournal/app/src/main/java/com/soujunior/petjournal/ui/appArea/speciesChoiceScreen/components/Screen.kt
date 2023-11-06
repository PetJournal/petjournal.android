package com.soujunior.petjournal.ui.appArea.speciesChoiceScreen.components

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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.accountManager.registerScreen.RegisterFormEvent
import com.soujunior.petjournal.ui.appArea.speciesChoiceScreen.PetFormEvent
import com.soujunior.petjournal.ui.appArea.speciesChoiceScreen.ViewModelChoiceSpecies
import com.soujunior.petjournal.ui.components.Button2
import com.soujunior.petjournal.ui.components.InputText
import com.soujunior.petjournal.ui.components.NavigationBar
import com.soujunior.petjournal.ui.components.ScaffoldCustom
import org.koin.core.component.getScopeName

@Composable
fun Screen(navController: NavController, viewModel: ViewModelChoiceSpecies) {
    val name by viewModel.name.collectAsState()
    val activateContinueButton = remember { mutableStateOf(false) }
    var isOthersFieldVisible by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf("") }

    ScaffoldCustom(
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsPadding(),
        titleTopBar = "Cadastro Pet",
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
                        Header(name = name.firstName)
                    }
                    item {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.background)
                        ) {
                            GridVectors { selectedSpecies ->
                                if (selectedSpecies.isNotEmpty()) activateContinueButton.value =
                                    true
                            }
                        }
                    }
                    item {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Button2(
                                submit = { isOthersFieldVisible = true },
                                modifier = Modifier.width(180.dp),
                                enableButton = true,
                                text = stringResource(R.string.others),
                            )
                        }
                    }
                    /*NOTA:
                    * Acredito que para o objetivo deste input, o ideal seria criar uma cópia do
                    * InputText a baixo, e modificar deacordo com o layout sugerido*/
                    item {
                        if (isOthersFieldVisible) {
                            InputText(
                                textTop = stringResource(id = R.string.insert_the_species),
                                textHint = stringResource(id = R.string.eg_species),
                                textValue = viewModel.state.specie,
                                textError = viewModel.state.specieError,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("input_specie"),
                                onEvent = { it: String -> viewModel.onEvent(PetFormEvent.OtherSpecie(it)) }
                            )
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
        })
}