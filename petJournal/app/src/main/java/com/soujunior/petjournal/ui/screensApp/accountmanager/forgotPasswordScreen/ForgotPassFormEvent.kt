package com.soujunior.petjournal.ui.screensApp.accountmanager.forgotPasswordScreen

sealed class ForgotPassFormEvent {
    data class EmailChanged(val email: String) : ForgotPassFormEvent()

    object Submit : ForgotPassFormEvent()
}
