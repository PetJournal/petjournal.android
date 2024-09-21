package com.soujunior.petjournal.ui.screens_app.account_manager.registerScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.components.Button2
import com.soujunior.petjournal.ui.components.Button3
import com.soujunior.petjournal.ui.components.CreateTitleAndImageLogo
import com.soujunior.petjournal.ui.components.DashedInputText
import com.soujunior.petjournal.ui.components.PrivacyPolicyCheckbox
import com.soujunior.petjournal.ui.components.mask.mobileNumberFilter
import com.soujunior.petjournal.ui.screens_app.account_manager.registerScreen.RegisterFormEvent
import com.soujunior.petjournal.ui.screens_app.account_manager.registerScreen.RegisterViewModel
import com.soujunior.petjournal.ui.states.TaskState
import ir.kaaveh.sdpcompose.sdp

@Composable
fun Screen(navController: NavController, viewModel: RegisterViewModel) {
    val taskState by viewModel.taskState.collectAsState()
    val systemUiController = rememberSystemUiController()
    val isDarkMode = isSystemInDarkTheme()
    systemUiController.setSystemBarsColor(color = Color.Transparent, darkIcons = true)
    systemUiController.setNavigationBarColor(Color.Black)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ) {

            CreateTitleAndImageLogo(
                title = stringResource(id = R.string.sign_up),
                spaceBetween = 40.sdp,
                styleTitle = MaterialTheme.typography.displayMedium
            )

        }
        Column(
            modifier = Modifier
                .systemBarsPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 20.sdp, end = 20.sdp, top = 140.sdp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top,
            ) {
                item {
                    DashedInputText(
                        titleText = stringResource(id = R.string.name),
                        placeholderText = stringResource(id = R.string.eg_enter_your_first_name),
                        textValue = viewModel.state.name,
                        textError = viewModel.state.nameError,
                        isError = !viewModel.state.nameError.isNullOrEmpty(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_name"),
                        onEvent = { it: String -> viewModel.onEvent(RegisterFormEvent.NameChanged(it)) }
                    )
                }
                item {
                    DashedInputText(
                        titleText = stringResource(id = R.string.lastname),
                        placeholderText = stringResource(id = R.string.eg_enter_your_last_name),
                        textValue = viewModel.state.lastName,
                        textError = viewModel.state.lastNameError,
                        isError = !viewModel.state.lastNameError.isNullOrEmpty(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_lastname"),
                        onEvent = { it: String ->
                            viewModel.onEvent(
                                RegisterFormEvent.LastNameChanged(
                                    it
                                )
                            )
                        }
                    )
                }
                item {
                    DashedInputText(
                        titleText = stringResource(id = R.string.email),
                        placeholderText = stringResource(id = R.string.eg_email),
                        textValue = viewModel.state.email,
                        textError = viewModel.state.emailError,
                        isError = !viewModel.state.emailError.isNullOrEmpty(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_email"),
                        onEvent = { it: String ->
                            viewModel.onEvent(
                                RegisterFormEvent.EmailChanged(
                                    it
                                )
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                    )
                }
                item {
                    DashedInputText(
                        titleText = stringResource(id = R.string.phone),
                        placeholderText = stringResource(id = R.string.eg_phone),
                        textValue = viewModel.state.phone,
                        textError = viewModel.state.phoneError,
                        isError = !viewModel.state.phoneError.isNullOrEmpty(),
                        hasAMask = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_phone"),
                        onEvent = { it: String ->
                            if (it.length <= 11) {
                                viewModel.onEvent(
                                    RegisterFormEvent.PhoneChanged(
                                        it
                                    )
                                )
                            }
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        visualTransformation = { mobileNumberFilter(it) }
                    )
                }
                item {
                    DashedInputText(
                        titleText = stringResource(id = R.string.password),
                        placeholderText = stringResource(id = R.string.eg_password),
                        textValue = viewModel.state.password,
                        textError = viewModel.state.passwordError,
                        isError = !viewModel.state.passwordError.isNullOrEmpty(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_password"),
                        isPassword = true,
                        onEvent = { it: String ->
                            viewModel.onEvent(
                                RegisterFormEvent.PasswordChanged(
                                    it
                                )
                            )
                        },
                    )
                }
                item {
                    DashedInputText(
                        titleText = stringResource(id = R.string.confirm_password),
                        placeholderText = stringResource(id = R.string.confirm_password),
                        textValue = viewModel.state.repeatedPassword,
                        textError = viewModel.state.repeatedPasswordError,
                        isError = !viewModel.state.repeatedPasswordError.isNullOrEmpty(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_confirm_password"),
                        isPassword = true,
                        onEvent = { it: String ->
                            viewModel.onEvent(
                                RegisterFormEvent.ConfirmPasswordChanged(
                                    it
                                )
                            )
                        },
                    )
                }
                item { Spacer(modifier = Modifier.height(5.sdp)) }
                item {
                    PrivacyPolicyCheckbox(
                        valueChecked = viewModel.state.privacyPolicy,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("checkbox"),
                        onEvent = { it: Boolean ->
                            viewModel.onEvent(
                                RegisterFormEvent.PrivacyPolicyChanged(
                                    it
                                )
                            )
                        }
                    )
                }
                item { Spacer(modifier = Modifier.height(5.sdp)) }
                item {


                    Row(
                        Modifier.padding(20.sdp)
                    ) {
                        Button3(
                            submit = { navController.popBackStack() },
                            enableButton = true,
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 5.sdp),
                            text = stringResource(R.string.back),
                            buttonColor = ButtonDefaults.buttonColors(
                                MaterialTheme.colorScheme.surface
                            ),
                            textColor = MaterialTheme.colorScheme.primary
                        )
                        Button2(

                            text = stringResource(id = R.string.register),
                            border = null,
                            buttonColor = if (isDarkMode) ButtonDefaults.buttonColors(MaterialTheme.colorScheme.onPrimary)
                            else ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
                            textColor = if (isDarkMode) MaterialTheme.colorScheme.primary else Color.White,
                            submit = {
                                viewModel.onEvent(RegisterFormEvent.Submit)
                            },
                            enableButton = viewModel.enableButton(),
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 5.sdp)
                                .testTag("button_register"),
                            isLoading = taskState is TaskState.Loading
                        )
                    }
                }

            }
        }
    }
}