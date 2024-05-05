package com.soujunior.petjournal.ui.screens_app.account_manager.forgotPasswordScreen

sealed class ForgotPasswordFormEvent {
    data class EmailChanged(val email: String) : ForgotPasswordFormEvent()
    object Submit : ForgotPasswordFormEvent()

}
