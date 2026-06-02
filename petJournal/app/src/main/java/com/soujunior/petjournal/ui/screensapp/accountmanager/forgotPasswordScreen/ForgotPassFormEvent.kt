package com.soujunior.petjournal.ui.screensapp.accountmanager.forgotPasswordScreen

sealed class ForgotPassFormEvent {
    data class EmailChanged(val email: String) : ForgotPassFormEvent()

    object Submit : ForgotPassFormEvent()
}
