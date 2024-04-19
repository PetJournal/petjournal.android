package com.soujunior.petjournal.ui.screens_app.account_manager.registerScreen.state

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf

object StatesRegister {
    var showPrivacyPolicy = compositionLocalOf { mutableStateOf(false) }
}