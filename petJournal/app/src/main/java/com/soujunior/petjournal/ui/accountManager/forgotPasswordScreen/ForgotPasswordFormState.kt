package com.soujunior.petjournal.ui.accountManager.forgotPasswordScreen

data class ForgotPasswordFormState(
    val email: String = "",
    val emailError: List<String>? = null
)