package com.soujunior.petjournal.ui.screensApp.accountmanager.registerScreen.state

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf

object StatesRegister {
    var showPrivacyPolicy = compositionLocalOf { mutableStateOf(false) }
}
