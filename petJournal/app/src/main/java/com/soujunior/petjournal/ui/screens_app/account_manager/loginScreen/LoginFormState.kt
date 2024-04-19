package com.soujunior.petjournal.ui.screens_app.account_manager.loginScreen

data class LoginFormState(
    val email: String = "",
    val emailError: List<String>? = null,
    val password: String = "",
    val passwordError: List<String>? = null,
    val rememberPassword: Boolean = false
)