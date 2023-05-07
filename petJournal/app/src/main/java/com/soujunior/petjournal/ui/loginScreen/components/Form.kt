package com.soujunior.petjournal.ui.loginScreen.components

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.soujunior.petjournal.ui.registerScreen.components.Email
import com.soujunior.petjournal.ui.registerScreen.components.Password

@Composable
fun Form(navController: NavController) {
    Email(isRegister = false)
    Password(isRegister = false)
    RememberForgotPassword(navController)}
