package com.soujunior.petjournal.ui.accountManager.forgotPasswordScreen

sealed class ForgotPassFormEvent {
    data class EmailChanged(val email: String) : ForgotPassFormEvent()
    object Submit : ForgotPassFormEvent()
}
