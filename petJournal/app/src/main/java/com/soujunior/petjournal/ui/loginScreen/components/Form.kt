package com.soujunior.petjournal.ui.loginScreen.components

import androidx.compose.runtime.Composable
import com.soujunior.petjournal.ui.registerScreen.components.Email
import com.soujunior.petjournal.ui.registerScreen.components.Password

@Composable
fun Form() {
    Email(isRegister = false)
    Password(isRegister = false)
    RememberForgotPassword()
}