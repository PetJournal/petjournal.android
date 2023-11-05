package com.soujunior.petjournal.ui.appArea.speciesChoiceScreen.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.components.Button2
import com.soujunior.petjournal.ui.components.NavigationBar
import com.soujunior.petjournal.ui.components.ScaffoldCustom

@Composable
fun Screen(navController: NavController) {
    val activateContinueButton = remember { mutableStateOf(false) }
    var isOthersFieldVisible by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf("") }

    ScaffoldCustom(
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsPadding(),
        titleTopBar = "Cadastro Pet",
        showButtonToReturn = true,
        navigationUp = navController,
        showTopBar = true,
        showBottomBarNavigation = true,
        bottomNavigationBar = { NavigationBar(navController) },
        contentToUse = {
            LazyColumn(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                item {
                    Header()
                }
                item {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.background)
                    ) {
                        GridVectors { selectedSpecies ->
                            if (selectedSpecies.isNotEmpty()) activateContinueButton.value = true
                        }
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(20.dp))
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
                item {
                    if (isOthersFieldVisible) {
                        Spacer(modifier = Modifier.height(20.dp))
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = stringResource(R.string.insert_the_species),
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Start
                            )
                            OutlinedTextField(
                                value = text,
                                onValueChange = { txt ->
                                    text = txt
                                },
                                modifier = Modifier.fillMaxWidth(),
                                placeholder = {
                                    Text(text = stringResource(R.string.type_here))
                                }
                            )
                        }

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
        })


}

@Preview(showBackground = true)
@Composable
fun Preview() {
    val navController = rememberNavController()
    Screen(navController = navController)
}