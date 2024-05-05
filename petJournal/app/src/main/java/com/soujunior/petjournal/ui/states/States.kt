package com.soujunior.petjournal.ui.states

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf

object States {
    var localEmailState = compositionLocalOf { mutableStateOf("") }
    var localEmailError = compositionLocalOf { mutableStateOf(false) }
    var localPasswordState = compositionLocalOf { mutableStateOf("") }
    var localPasswordError = compositionLocalOf { mutableStateOf(false) }
    var localConfirmPasswordState = compositionLocalOf { mutableStateOf("") }
    var localConfirmPasswordError = compositionLocalOf { mutableStateOf(false) }
    var checked = compositionLocalOf { mutableStateOf(false) }
    var otpFullCode = compositionLocalOf { mutableStateOf("") }
}