package com.soujunior.petjournal.ui.screens_app.screens_pets.registerPetScreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.components.Button3
import com.soujunior.petjournal.ui.components.DateInputText
import com.soujunior.petjournal.ui.components.DropDown
import com.soujunior.petjournal.ui.components.DualActionButton
import com.soujunior.petjournal.ui.components.ImagePet
import com.soujunior.petjournal.ui.components.InputText
import com.soujunior.petjournal.ui.components.NavigationBar
import com.soujunior.petjournal.ui.components.ScaffoldCustom
import com.soujunior.petjournal.ui.theme.PetJournalTheme
import ir.kaaveh.sdpcompose.sdp

@Composable
fun Screen(navController: NavController) {
    Column(modifier = Modifier) {
        ScaffoldCustom(
            modifier = Modifier,
            navigationUp = navController,
            showTopBar = true,
            titleTopBar = stringResource(R.string.edit_pet_data),
            showBottomBarNavigation = true,
            bottomNavigationBar = { NavigationBar(navController) },
            contentToUse = {
                Image(
                    painter = painterResource(R.drawable.rastro),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .offset(y = 300.sdp)
                        .align(AbsoluteAlignment.Left)
                )
                LazyColumn(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top,
                    modifier = Modifier
                        .fillMaxSize()
                        .fillMaxHeight()
                        .padding(
                            top = it.calculateTopPadding(),
                            bottom = it.calculateBottomPadding() + 2.sdp
                        )
                        .background(Color.Transparent),
                    content = {
                        item {
                            ImagePet()
                        }
                        item {
                            InputText(
                                textInputModifier = Modifier
                                    .testTag("inputField_test")
                                    .padding(
                                        start = 24.sdp,
                                        end = 24.sdp,
                                        bottom = 12.sdp,
                                        top = 4.sdp
                                    ),
                                placeholderText = stringResource(R.string.placeholder_name_pet),
                                titleText = stringResource(R.string.pet_name),
                                textValue = "",
                                onEvent = { }
                            )
                        }
                        item {
                            DropDown(
                                textInputModifier = Modifier.padding(
                                    start = 24.sdp,
                                    end = 24.sdp,
                                    bottom = 12.sdp,
                                    top = 4.sdp
                                ),
                                placeholderText = stringResource(R.string.placeholder_breed),
                                titleText = stringResource(R.string.breed),
                                textValue = "",
                                onEvent = { }
                            )
                        }
                        item {
                            DropDown(
                                textInputModifier = Modifier.padding(
                                    start = 24.sdp,
                                    end = 24.sdp,
                                    bottom = 12.sdp,
                                    top = 4.sdp
                                ),
                                placeholderText = stringResource(R.string.placeholder_size),
                                titleText = stringResource(R.string.size),
                                textValue = "",
                                onEvent = { }
                            )
                        }
                        item {
                            DateInputText(
                                textInputModifier = Modifier.padding(
                                    start = 24.sdp,
                                    end = 24.sdp,
                                    bottom = 12.sdp,
                                    top = 4.sdp
                                ),
                                titleText = stringResource(R.string.pet_birth_date),
                                placeholderText = stringResource(R.string.placeholder_text_DD_MM_YYYY),
                                textValue = "",
                                onEvent = { },
                                // Será necessário criar uma função para formatar a data
//                                visualTransformation = { date ->
//                                    formatDate(date)
//                                }
                            )
                        }
                        item {
                            DropDown(
                                textInputModifier = Modifier.padding(
                                    start = 24.sdp,
                                    end = 24.sdp,
                                    bottom = 12.sdp,
                                    top = 4.sdp
                                ),
                                placeholderText = stringResource(R.string.placeholder_weight),
                                titleText = stringResource(R.string.weight),
                                textValue = "",
                                onEvent = { }
                            )
                        }
                        item {
                            DropDown(
                                textInputModifier = Modifier.padding(
                                    start = 24.sdp,
                                    end = 24.sdp,
                                    bottom = 12.sdp,
                                    top = 4.sdp
                                ),
                                placeholderText = stringResource(R.string.placeholder_type),
                                titleText = stringResource(R.string.type),
                                textValue = "",
                                onEvent = { }
                            )
                        }
                        item {
                            DualActionButton(
                                buttonModifier = Modifier.padding(
                                    start = 24.sdp,
                                    end = 24.sdp,
                                    bottom = 12.sdp,
                                    top = 4.sdp
                                ),
                                titleText = stringResource(R.string.pet_sex),
                                rightButtonSubmit = {},
                                leftButtonSubmit = {},
                                enableButton = true,
                                rightButtonText = stringResource(R.string.male),
                                leftButtonText = stringResource(R.string.female)
                            )
                        }
                        item {
                            DualActionButton(
                                buttonModifier = Modifier.padding(
                                    start = 24.sdp,
                                    end = 24.sdp,
                                    bottom = 12.sdp,
                                    top = 4.sdp
                                ),
                                titleText = stringResource(R.string.castrated),
                                rightButtonSubmit = {},
                                leftButtonSubmit = {},
                                enableButton = true,
                                rightButtonText = stringResource(R.string.yes),
                                leftButtonText = stringResource(R.string.no),
                                rightButtonColor = ButtonDefaults.buttonColors(androidx.compose.material3.MaterialTheme.colorScheme.background),
                                leftButtonColor = ButtonDefaults.buttonColors(androidx.compose.material3.MaterialTheme.colorScheme.primary),
                                rightButtonTextColor = androidx.compose.material3.MaterialTheme.colorScheme.primary,
                                leftButtonTextColor = androidx.compose.material3.MaterialTheme.colorScheme.onPrimary,
                            )
                        }
                        item {
                            Spacer(modifier = Modifier.height(20.sdp))
                        }
                        item {
                            Button3(
                                submit = {},
                                enableButton = true,
                                text = stringResource(R.string.save)
                            )
                        }
                    }
                )
            }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true, device = "id:pixel_4_xl")
@Composable
fun ScreenPreview() {
    val nav = rememberNavController()
    PetJournalTheme(
        content = {
            Screen(nav)
        }
    )
}