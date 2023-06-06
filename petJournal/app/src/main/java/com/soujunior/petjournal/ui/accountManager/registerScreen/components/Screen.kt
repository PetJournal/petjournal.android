package com.soujunior.petjournal.ui.accountManager.registerScreen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.soujunior.petjournal.ui.accountManager.registerScreen.RegisterFormEvent
import com.soujunior.petjournal.ui.accountManager.registerScreen.RegisterScreenViewModel
import com.soujunior.petjournal.ui.components.Button
import com.soujunior.petjournal.ui.components.CreateTitleAndImageLogo
import com.soujunior.petjournal.ui.components.InputText
import com.soujunior.petjournal.ui.components.PrivacyPolicyCheckbox
import com.soujunior.petjournal.ui.components.mask.mobileNumberFilter

@Composable
fun Screen(viewModel: RegisterScreenViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 20.dp, end = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top,
        ) {
            item { CreateTitleAndImageLogo("Inscreva-se", spaceBottom = 10.dp) }
            item { Spacer(modifier = Modifier.height(15.dp)) }
            item {
                InputText(
                    textTop = "Nome",
                    textHint = "Digite seu primeiro nome",
                    textValue = viewModel.state.name,
                    textError = viewModel.state.nameError,
                    onEvent = { it: String -> viewModel.onEvent(RegisterFormEvent.NameChanged(it)) }
                )
            }
            item { Spacer(modifier = Modifier.height(15.dp)) }
            item {
                InputText(
                    textTop = "Sobrenome",
                    textHint = "Digite seu sobrenome",
                    textValue = viewModel.state.lastName,
                    textError = viewModel.state.lastNameError,
                    onEvent = { it: String -> viewModel.onEvent(RegisterFormEvent.LastNameChanged(it)) }
                )
            }
            item { Spacer(modifier = Modifier.height(15.dp)) }
            item {
                InputText(
                    textTop = "Email",
                    textHint = "eg: exemple@petjournal.com",
                    textValue = viewModel.state.email,
                    textError = viewModel.state.emailError,
                    onEvent = { it: String -> viewModel.onEvent(RegisterFormEvent.EmailChanged(it)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )
            }
            item { Spacer(modifier = Modifier.height(15.dp)) }
            item {
                InputText(
                    textTop = "Telefone",
                    textHint = "eg: 91 9 1234-4567",
                    textValue = viewModel.state.phone,
                    textError = viewModel.state.phoneError,
                    onEvent = { it: String -> viewModel.onEvent(RegisterFormEvent.PhoneChanged(it)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    visualTransformation = { mobileNumberFilter(it) }
                )
            }
            item { Spacer(modifier = Modifier.height(15.dp)) }
            item {
                InputText(
                    textTop = "Senha",
                    textHint = "Digite sua senha",
                    textValue = viewModel.state.password,
                    textError = viewModel.state.passwordError,
                    isPassword = true,
                    onEvent = { it: String -> viewModel.onEvent(RegisterFormEvent.PasswordChanged(it)) },
                )
            }
            item { Spacer(modifier = Modifier.height(15.dp)) }
            item {
                InputText(
                    textTop = "Confirmar senha",
                    textHint = "Confirme sua senha",
                    textValue = viewModel.state.repeatedPassword,
                    textError = viewModel.state.repeatedPasswordError,
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
                    text = "Cadastrar",
                    border = null,
                    submit = {
                        viewModel.onEvent(RegisterFormEvent.Submit)
                    },
                    enableButton = viewModel.enableButton(),
                    setSystemBarColor = true,
                    inDarkMode = true
                )
            }
        }
    }
}