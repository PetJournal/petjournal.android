package com.soujunior.petjournal.ui.screens_app.account_manager.forgotPasswordScreen

data class ForgotPasswordFormState(
    val email: String = "",
    val emailError: List<String>? = null
)