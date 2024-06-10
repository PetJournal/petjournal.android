package com.soujunior.petjournal.ui.screens_app.account_manager.registerScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.components.Button
import com.soujunior.petjournal.ui.components.CreateTitleAndImageLogo
import com.soujunior.petjournal.ui.components.InputText
import com.soujunior.petjournal.ui.components.PrivacyPolicyCheckbox
import com.soujunior.petjournal.ui.components.mask.mobileNumberFilter
import com.soujunior.petjournal.ui.screens_app.account_manager.registerScreen.RegisterFormEvent
import com.soujunior.petjournal.ui.screens_app.account_manager.registerScreen.RegisterViewModel
import com.soujunior.petjournal.ui.states.TaskState

@Composable
fun Screen(viewModel: RegisterViewModel) {
    val taskState by viewModel.taskState.collectAsState()
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(color = Color.Transparent, darkIcons = true)
    systemUiController.setNavigationBarColor(Color.Black)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
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
                    .padding(start = 20.dp, end = 20.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top,
            ) {
                item {
                    CreateTitleAndImageLogo(
                        stringResource(id = R.string.sign_up),
                        spaceBottom = 10.dp
                    )
                }
                item { Spacer(modifier = Modifier.height(15.dp)) }
                item {
                    InputText(
                        textTop = stringResource(id = R.string.name),
                        textHint = stringResource(id = R.string.eg_enter_your_first_name),
                        textValue = viewModel.state.name,
                        textError = viewModel.state.nameError,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_name"),
                        onEvent = { it: String -> viewModel.onEvent(RegisterFormEvent.NameChanged(it)) }
                    )
                }
                item { Spacer(modifier = Modifier.height(15.dp)) }
                item {
                    InputText(
                        textTop = stringResource(id = R.string.lastname),
                        textHint = stringResource(id = R.string.eg_enter_your_last_name),
                        textValue = viewModel.state.lastName,
                        textError = viewModel.state.lastNameError,
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
                item { Spacer(modifier = Modifier.height(15.dp)) }
                item {
                    InputText(
                        textTop = stringResource(id = R.string.email),
                        textHint = stringResource(id = R.string.eg_email),
                        textValue = viewModel.state.email,
                        textError = viewModel.state.emailError,
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
                item { Spacer(modifier = Modifier.height(15.dp)) }
                item {
                    InputText(
                        textTop = stringResource(id = R.string.phone),
                        textHint = stringResource(id = R.string.eg_phone),
                        textValue = viewModel.state.phone,
                        textError = viewModel.state.phoneError,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_phone"),
                        onEvent = {  it: String ->
                            if(viewModel.state.phone.length < 11)
                                viewModel.onEvent(RegisterFormEvent.PhoneChanged(it))
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        visualTransformation = { mobileNumberFilter(it) }
                    )
                }
                item { Spacer(modifier = Modifier.height(15.dp)) }
                item {
                    InputText(
                        textTop = stringResource(id = R.string.password),
                        textHint = stringResource(id = R.string.eg_password),
                        textValue = viewModel.state.password,
                        textError = viewModel.state.passwordError,
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
                item { Spacer(modifier = Modifier.height(15.dp)) }
                item {
                    InputText(
                        textTop = stringResource(id = R.string.confirm_password),
                        textHint = stringResource(id = R.string.confirm_password),
                        textValue = viewModel.state.repeatedPassword,
                        textError = viewModel.state.repeatedPasswordError,
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
                item { Spacer(modifier = Modifier.height(5.dp)) }
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
                item { Spacer(modifier = Modifier.height(5.dp)) }
                item {
                    Button(
                        text = stringResource(id = R.string.register),
                        border = null,
                        submit = {
                            viewModel.onEvent(RegisterFormEvent.Submit)
                        },
                        enableButton = viewModel.enableButton(),
                        setSystemBarColor = true,
                        inDarkMode = true,
                        modifier = Modifier
                            .padding(bottom = 50.dp)
                            .testTag("button_register"),
                        isLoading = taskState is TaskState.Loading
                    )
                }
            }
        }
    }
}