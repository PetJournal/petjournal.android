package com.soujunior.petjournal.ui.screensApp.accountmanager.changePasswordScreen

sealed class ChangePasswordFormEvent {
    data class PasswordChanged(val password: String) : ChangePasswordFormEvent()

    data class ConfirmPasswordChanged(val confirmPassword: String) : ChangePasswordFormEvent()

    data class DisconnectOtherDevices(val disconnect: Boolean) : ChangePasswordFormEvent()

    object Submit : ChangePasswordFormEvent()
}
