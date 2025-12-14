package com.soujunior.petjournal.ui.screensApp.accountmanager.loginScreen

sealed class LoginFormEvent {
    data class EmailChanged(val email: String) : LoginFormEvent()

    data class PasswordChanged(val password: String) : LoginFormEvent()

    data class RememberPassword(val isRemember: Boolean) : LoginFormEvent()

    object Submit : LoginFormEvent()
}
