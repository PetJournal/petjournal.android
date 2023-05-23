package com.soujunior.petjournal.ui.accountManager.loginScreen.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.soujunior.petjournal.ui.accountManager.loginScreen.LoginFormEvent
import com.soujunior.petjournal.ui.accountManager.loginScreen.LoginScreenViewModel
import com.soujunior.petjournal.ui.components.EmailRefactor
import com.soujunior.petjournal.ui.components.PasswordRefactor

@Composable
fun Form(navController: NavController, viewModel: LoginScreenViewModel) {
    EmailRefactor(
        isRegister = false,
        email = viewModel.state.email,
        emailError = viewModel.state.emailError,
        onEvent = { it: String -> viewModel.onEvent(LoginFormEvent.EmailChanged(it)) }
    )

    Spacer(modifier = Modifier.padding(top = 30.dp))

    PasswordRefactor(
        isRegister = false,
        password = viewModel.state.password,
        passwordErrorList = viewModel.state.passwordError,
        onEvent = { it: String -> viewModel.onEvent(LoginFormEvent.PasswordChanged(it)) }
    )

    RememberForgotPassword(
        navController = navController,
        checkBox = viewModel.state.rememberPassword,
        checkboxState = { it: Boolean -> viewModel.onEvent(LoginFormEvent.RememberPassword(it)) }
    )
}