package com.soujunior.petjournal.ui.accountManager.loginScreen.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.soujunior.petjournal.ui.components.Email
import com.soujunior.petjournal.ui.components.Password

@Composable
fun Form(navController: NavController) {
    Email(isRegister = false)
    Spacer(modifier = Modifier.padding(top = 30.dp))
    Password(isRegister = false)
    RememberForgotPassword(navController)
}
