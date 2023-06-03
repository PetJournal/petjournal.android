package com.soujunior.petjournal.ui.accountManager.forgotPasswordScreen

data class ForgotPassFormState(
    val email: String = "",
    val emailError: List<String>? = null,
)