package com.soujunior.petjournal.ui.screens_app.account_manager.forgotPasswordScreen

sealed class ForgotPassFormEvent {
    data class EmailChanged(val email: String) : ForgotPassFormEvent()
    object Submit : ForgotPassFormEvent()
}
