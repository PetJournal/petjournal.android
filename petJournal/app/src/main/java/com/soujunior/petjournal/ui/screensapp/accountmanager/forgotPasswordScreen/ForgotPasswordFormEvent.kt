package com.soujunior.petjournal.ui.screensapp.accountmanager.forgotPasswordScreen

sealed class ForgotPasswordFormEvent {
    data class EmailChanged(val email: String) : ForgotPasswordFormEvent()

    object Submit : ForgotPasswordFormEvent()
}
