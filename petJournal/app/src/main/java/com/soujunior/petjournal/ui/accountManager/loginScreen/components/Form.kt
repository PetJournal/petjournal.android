package com.soujunior.petjournal.ui.accountManager.loginScreen.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.soujunior.petjournal.ui.accountManager.loginScreen.LoginFormEvent
import com.soujunior.petjournal.ui.accountManager.loginScreen.LoginViewModel
import com.soujunior.petjournal.ui.components.InputText

@Composable
fun Form(navController: NavController, viewModel: LoginViewModel) {
    InputText(
        textTop = "Email",
        textHint = "eg: exemple@petjournal.com",
        textValue = viewModel.state.email,
        textError = viewModel.state.emailError,
        onEvent = { it: String -> viewModel.onEvent(LoginFormEvent.EmailChanged(it)) }
    )

    Spacer(modifier = Modifier.padding(top = 30.dp))
    InputText(
        textTop = "Senha",
        textHint = "Digite sua senha",
        textValue = viewModel.state.password,
        textError = viewModel.state.passwordError,
        onEvent = { it: String -> viewModel.onEvent(LoginFormEvent.PasswordChanged(it)) }
    )

    RememberForgotPassword(
        navController = navController,
        checkBox = viewModel.state.rememberPassword,
        onEvent = { it: Boolean -> viewModel.onEvent(LoginFormEvent.RememberPassword(it)) }
    )
}