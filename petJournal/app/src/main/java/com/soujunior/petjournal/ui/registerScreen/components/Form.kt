package com.soujunior.petjournal.ui.registerScreen.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun Form(modifier: Modifier) {
    Name(modifier = modifier)
    LastName(modifier = modifier)
    Email()
    PhoneNumber(modifier = modifier)
    Password()
    ConfirmPassword(modifier = modifier)
    PrivacyPolicyCheckbox()
}
