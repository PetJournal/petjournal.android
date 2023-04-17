package com.soujunior.petjournal.ui.registerScreen.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun Form(modifier: Modifier) {
    Name(modifier = modifier)
    LastName(modifier = modifier)
    Email(modifier = modifier)
    PhoneNumber(modifier = modifier)
    Password(modifier = modifier)
    ConfirmPassword(modifier = modifier)
    PrivacyPolicyCheckbox()
}
