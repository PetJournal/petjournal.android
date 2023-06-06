package com.soujunior.petjournal.ui.accountManager.registerScreen

data class RegisterFormState(
    val name: String = "",
    val nameError: List<String>? = null,
    val lastName: String = "",
    val lastNameError: List<String>? = null,
    val email: String = "",
    val emailError: List<String>? = null,
    val phone: String = "",
    val phoneError: List<String>? = null,
    val password: String = "",
    val passwordError: List<String>? = null,
    val repeatedPassword: String = "",
    val repeatedPasswordError: List<String>? = null,
    val privacyPolicy: Boolean = false,
)
