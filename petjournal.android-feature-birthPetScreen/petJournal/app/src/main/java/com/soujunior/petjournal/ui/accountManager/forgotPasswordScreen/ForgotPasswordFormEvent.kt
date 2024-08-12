package com.soujunior.petjournal.ui.accountManager.forgotPasswordScreen

sealed class ForgotPasswordFormEvent {
    data class EmailChanged(val email: String) : ForgotPasswordFormEvent()
    object Submit : ForgotPasswordFormEvent()

}
