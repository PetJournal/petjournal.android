package com.soujunior.petjournal.ui.screens_app.account_manager.loginScreen

sealed class LoginFormEvent {
    data class EmailChanged(val email: String) : LoginFormEvent()
    data class PasswordChanged(val password: String) : LoginFormEvent()
    data class RememberPassword(val isRemember: Boolean) : LoginFormEvent()
    object Submit : LoginFormEvent()
}
