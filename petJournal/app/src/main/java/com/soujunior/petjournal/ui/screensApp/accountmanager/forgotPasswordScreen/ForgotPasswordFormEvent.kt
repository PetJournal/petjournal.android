package com.soujunior.petjournal.ui.screensApp.accountmanager.forgotPasswordScreen

sealed class ForgotPasswordFormEvent {
    data class EmailChanged(val email: String) : ForgotPasswordFormEvent()

    object Submit : ForgotPasswordFormEvent()
}
