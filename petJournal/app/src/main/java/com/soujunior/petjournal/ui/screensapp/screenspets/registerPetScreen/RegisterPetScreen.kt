package com.soujunior.petjournal.ui.screensapp.screenspets.registerPetScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
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
import com.soujunior.petjournal.ui.theme.PetJournalTheme
import ir.kaaveh.sdpcompose.sdp

@Composable
fun RegisterPetScreen(navController: NavController) {
    Column(modifier = Modifier) {
        ScaffoldCustom(
            modifier =
                Modifier
                    .navigationBarsPadding()
                    .fillMaxSize(),
            navigationUp = navController,
            showTopBar = true,
            showButtonToReturn = true,
            titleTopBar = stringResource(R.string.edit_pet_data),
            showBottomBarNavigation = true,
            bottomNavigationBar = { NavigationBar(navController) },
            contentToUse = {
                Image(
                    painter = painterResource(R.drawable.rastro),
                    contentDescription = null,
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .offset(y = 300.sdp)
                            .align(AbsoluteAlignment.Left),
                )

                LazyColumn(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.sdp),
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .fillMaxHeight()
                            .padding(it)
                            .background(Color.Transparent),
                    contentPadding = PaddingValues(horizontal = 24.sdp),
                    content = {
                        item {
                            ImagePet()
                        }
                        item {
                            InputText(
                                modifier = Modifier,
                                textTitleModifier = Modifier.padding(bottom = 4.sdp),
                                textInputModifier = Modifier.testTag("inputField_test"),
                                placeholderText = stringResource(R.string.placeholder_name_pet),
                                titleText = stringResource(R.string.pet_name),
                                textValue = "",
                                onEvent = { },
                            )
                        }
                        item {
                            DropDown(
                                textInputModifier = Modifier,
                                textTitleModifier = Modifier.padding(bottom = 4.sdp),
                                placeholderText = stringResource(R.string.placeholder_breed),
                                titleText = stringResource(R.string.breed),
                                textValue = "",
                                onEvent = { },
                            )
                        }
                        item {
                            DropDown(
                                textInputModifier = Modifier,
                                textTitleModifier = Modifier.padding(bottom = 4.sdp),
                                placeholderText = stringResource(R.string.placeholder_size),
                                titleText = stringResource(R.string.size),
                                textValue = "",
                                onEvent = { },
                            )
                        }
                        item {
                            DateInputText(
                                textInputModifier = Modifier,
                                textTitleModifier = Modifier.padding(bottom = 4.sdp),
                                titleText = stringResource(R.string.pet_birth_date),
                                placeholderText = stringResource(R.string.placeholder_text_DD_MM_YYYY),
                                textValue = "",
                                onEvent = { },
                                // visualTransformation = { date ->
                                //     formatDate(date)
                                // }
                            )
                        }
                        item {
                            DropDown(
                                textTitleModifier = Modifier.padding(bottom = 4.sdp),
                                placeholderText = stringResource(id = R.string.placeholder_weight),
                                titleText = stringResource(id = R.string.weight),
                                textValue = "",
                                onEvent = { },
                            )
                        }
                        item {
                            DropDown(
                                textInputModifier = Modifier.padding(top = 4.sdp),
                                placeholderText = stringResource(R.string.placeholder_type),
                                titleText = stringResource(R.string.type),
                                textValue = "",
                                onEvent = { },
                            )
                        }
                        item {
                            Column {
                                Row {
                                    Text(
                                        text = stringResource(R.string.pet_sex),
                                        textAlign = TextAlign.Start,
                                        color = MaterialTheme.colorScheme.scrim,
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight(500),
                                        modifier =
                                            Modifier
                                                .padding(bottom = 8.sdp)
                                                .fillMaxWidth(),
                                    )
                                }
                                DualActionButton(
                                    buttonModifier = Modifier,
                                    titleText = stringResource(R.string.pet_sex),
                                    rightButtonSubmit = {},
                                    leftButtonSubmit = {},
                                    enableButton = true,
                                    leftButtonText = stringResource(R.string.male),
                                    rightButtonText = stringResource(R.string.female),
                                )
                            }
                        }
                        item {
                            Column {
                                Row {
                                    Text(
                                        text = stringResource(R.string.pet_registration),
                                        textAlign = TextAlign.Start,
                                        color = MaterialTheme.colorScheme.scrim,
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight(500),
                                        modifier =
                                            Modifier
                                                .padding(bottom = 8.sdp)
                                                .fillMaxWidth(),
                                    )
                                }
                                DualActionButton(
                                    buttonModifier = Modifier,
                                    titleText = stringResource(R.string.castrated),
                                    rightButtonSubmit = {},
                                    leftButtonSubmit = {},
                                    enableButton = true,
                                    leftButtonText = stringResource(R.string.yes),
                                    rightButtonText = stringResource(R.string.no),
                                    leftButtonColor = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.background),
                                    rightButtonColor = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
                                    leftButtonTextColor = MaterialTheme.colorScheme.primary,
                                    rightButtonTextColor = MaterialTheme.colorScheme.onPrimary,
                                )
                            }
                        }
                        item {
                            Spacer(modifier = Modifier.height(20.sdp))
                        }
                        item {
                            Button3(
                                submit = {},
                                enableButton = true,
                                text = stringResource(R.string.save),
                            )
                        }
                    },
                )
            },
        )
    }
}

@Preview(showBackground = true, showSystemUi = true, device = "id:pixel_4_xl")
@Composable
fun ScreenPreview() {
    val nav = rememberNavController()
    PetJournalTheme(
        content = {
            RegisterPetScreen(nav)
        },
    )
}

@Preview(showBackground = true)
@Composable
fun InputTextPreview() {
    PetJournalTheme {
        InputText(
            modifier = Modifier,
            textTitleModifier = Modifier.padding(bottom = 4.sdp),
            textInputModifier = Modifier.testTag("inputField_test"),
            placeholderText = stringResource(R.string.placeholder_name_pet),
            titleText = stringResource(R.string.pet_name),
            textValue = "",
            onEvent = { },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DropDownBreedPreview() {
    PetJournalTheme {
        DropDown(
            textInputModifier = Modifier,
            textTitleModifier = Modifier.padding(bottom = 4.sdp),
            placeholderText = stringResource(R.string.placeholder_breed),
            titleText = stringResource(R.string.breed),
            textValue = "",
            onEvent = { },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DropDownSizePreview() {
    PetJournalTheme {
        DropDown(
            textInputModifier = Modifier,
            textTitleModifier = Modifier.padding(bottom = 4.sdp),
            placeholderText = stringResource(R.string.placeholder_size),
            titleText = stringResource(R.string.size),
            textValue = "",
            onEvent = { },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DateInputTextPreview() {
    PetJournalTheme {
        DateInputText(
            textInputModifier = Modifier,
            textTitleModifier = Modifier.padding(bottom = 4.sdp),
            titleText = stringResource(R.string.pet_birth_date),
            placeholderText = stringResource(R.string.placeholder_text_DD_MM_YYYY),
            textValue = "",
            onEvent = { },
            // visualTransformation = { date ->
            //     formatDate(date)
            // }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DropDownWeightPreview() {
    PetJournalTheme {
        DropDown(
            textTitleModifier = Modifier.padding(bottom = 4.sdp),
            placeholderText = stringResource(id = R.string.placeholder_weight),
            titleText = stringResource(id = R.string.weight),
            textValue = "",
            onEvent = { },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DropDownTypePreview() {
    PetJournalTheme {
        DropDown(
            textInputModifier = Modifier.padding(top = 4.sdp),
            placeholderText = stringResource(R.string.placeholder_type),
            titleText = stringResource(R.string.type),
            textValue = "",
            onEvent = { },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DualActionSexPreview() {
    PetJournalTheme {
        Column {
            Row {
                Text(
                    text = stringResource(R.string.pet_sex),
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colorScheme.scrim,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight(500),
                    modifier =
                        Modifier
                            .padding(bottom = 8.sdp)
                            .fillMaxWidth(),
                )
            }
            DualActionButton(
                buttonModifier = Modifier,
                titleText = stringResource(R.string.pet_sex),
                rightButtonSubmit = {},
                leftButtonSubmit = {},
                enableButton = true,
                leftButtonText = stringResource(R.string.male),
                rightButtonText = stringResource(R.string.female),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DualActionCastratedPreview() {
    PetJournalTheme {
        Column {
            Row {
                Text(
                    text = stringResource(R.string.pet_registration),
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colorScheme.scrim,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight(500),
                    modifier =
                        Modifier
                            .padding(bottom = 8.sdp)
                            .fillMaxWidth(),
                )
            }
            DualActionButton(
                buttonModifier = Modifier,
                titleText = stringResource(R.string.castrated),
                rightButtonSubmit = {},
                leftButtonSubmit = {},
                enableButton = true,
                leftButtonText = stringResource(R.string.yes),
                rightButtonText = stringResource(R.string.no),
                leftButtonColor = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.background),
                rightButtonColor = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
                leftButtonTextColor = MaterialTheme.colorScheme.primary,
                rightButtonTextColor = MaterialTheme.colorScheme.onPrimary,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Button3SavePreview() {
    PetJournalTheme {
        Button3(
            submit = {},
            enableButton = true,
            text = stringResource(R.string.save),
        )
    }
}
