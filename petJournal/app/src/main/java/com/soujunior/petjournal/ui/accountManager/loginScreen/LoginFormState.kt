package com.soujunior.petjournal.ui.accountManager.loginScreen

data class LoginFormState(
    val email: String = "",
    val emailError: List<String>? = null,
    val password: String = "",
    val passwordError: List<String>? = null,
    val rememberPassword: Boolean = false
)