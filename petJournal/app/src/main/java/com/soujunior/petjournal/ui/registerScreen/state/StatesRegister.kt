package com.soujunior.petjournal.ui.registerScreen.state

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf

object StatesRegister {
    var localNameState = compositionLocalOf { mutableStateOf("") }
    var localNameError = compositionLocalOf { mutableStateOf(false) }
    var localLastNameState = compositionLocalOf { mutableStateOf("") }
    var localLastNameError = compositionLocalOf { mutableStateOf(false) }
    var localEmailState = compositionLocalOf { mutableStateOf("") }
    var localEmailError = compositionLocalOf { mutableStateOf(false) }
    var localPhoneNumberState = compositionLocalOf { mutableStateOf("") }
    var localPhoneNumberError = compositionLocalOf { mutableStateOf(false) }
    var localPasswordState = compositionLocalOf { mutableStateOf("") }
    var localPasswordError = compositionLocalOf { mutableStateOf(false) }
    var localConfirmPasswordState = compositionLocalOf { mutableStateOf("") }
    var localConfirmPasswordError = compositionLocalOf { mutableStateOf(false) }
    var localCheckedState = compositionLocalOf { mutableStateOf(false) }
    var showPrivacyPolicy = compositionLocalOf { mutableStateOf(false) }
}