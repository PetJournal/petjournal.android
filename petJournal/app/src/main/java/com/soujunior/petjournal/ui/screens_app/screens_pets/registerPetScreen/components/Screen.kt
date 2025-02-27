package com.soujunior.petjournal.ui.screens_app.screens_pets.registerPetScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
import com.soujunior.petjournal.ui.components.mask.formatDate
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun Screen(navController: NavController) {
    Column(modifier = Modifier.navigationBarsPadding()) {
        ScaffoldCustom(
            modifier = Modifier,
            navigationUp = navController,
            showTopBar = true,
            titleTopBar = stringResource(R.string.edit_pet_data),
            showBottomBarNavigation = true,
            bottomNavigationBar = { NavigationBar(navController) },
            contentToUse = {
                LazyColumn(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top,
                    modifier = Modifier
                        .fillMaxSize()
                        .fillMaxHeight()
                        .padding(
                            start = 16.sdp,
                            top = 24.sdp,
                            end = 16.sdp,
                            bottom = it.calculateBottomPadding() + 1.sdp
                        )
                        .background(MaterialTheme.colors.background),
                    content = {
                        item {
                            ImagePet()
                        }
                        item {
                            InputText(
                                modifier = Modifier,
                                placeholderText = stringResource(R.string.name_your_pet),
                                titleText = stringResource(id = R.string.pet_name),
                                textValue = "",
                                onEvent = { }
                            )
                        }
                        item {
                            DropDown(
                                modifier = Modifier,
                                textInputModifier = Modifier,
                                placeholderText = stringResource(R.string.pet_breed),
                                titleText = stringResource(R.string.breed),
                                textValue = "",
                                onEvent = { }
                            )
                        }
                        item {
                            DropDown(
                                modifier = Modifier,
                                textInputModifier = Modifier,
                                placeholderText = stringResource(R.string.pet_size),
                                titleText = stringResource(R.string.size),
                                textValue = "",
                                onEvent = { }
                            )
                        }
                        item {
                            DateInputText(
                                titleText = stringResource(R.string.pet_birth_date),
                                placeholderText = stringResource(R.string.placeholder_text_DD_MM_YYYY),
                                textValue = "",
                                modifier = Modifier
                                    .fillMaxWidth(),
                                onEvent = { },
                                visualTransformation = { formatDate(it) }
                            )
                        }
                        item {
                            DropDown(
                                modifier = Modifier,
                                textInputModifier = Modifier,
                                placeholderText = stringResource(R.string.pet_weight),
                                titleText = stringResource(R.string.weight),
                                textValue = "",
                                onEvent = { }
                            )
                        }
                        item {
                            DropDown(
                                modifier = Modifier,
                                textInputModifier = Modifier,
                                placeholderText = stringResource(R.string.pet_type),
                                titleText = stringResource(R.string.type),
                                textValue = "",
                                onEvent = { }
                            )
                        }
                        item {
                            Text(
                                text = stringResource(R.string.pet_sex),
                                textAlign = TextAlign.Start,
                                color = androidx.compose.material3.MaterialTheme.colorScheme.onBackground,
                                style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                                fontSize = 14.ssp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 10.sdp, bottom = 5.sdp, top = 15.sdp)
                            )
                        }
                        item {
                            DualActionButton(
                                rightButtonSubmit = {},
                                leftButtonSubmit = {},
                                enableButton = true,
                                rightButtonText = stringResource(R.string.male),
                                leftButtonText = stringResource(R.string.female)
                            )
                        }
                        item {
                            Text(
                                text = stringResource(R.string.castrated),
                                textAlign = TextAlign.Start,
                                color = androidx.compose.material3.MaterialTheme.colorScheme.onBackground,
                                style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                                fontSize = 14.ssp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 10.sdp, bottom = 5.sdp, top = 15.sdp)
                            )
                        }
                        item {
                            DualActionButton(
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
                            Spacer(modifier = Modifier.height(50.sdp))
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
    Screen(nav)
}