package com.soujunior.petjournal.ui.accountManager.changePasswordScreen

data class ChangePasswordFormState(
    val password: String = "",
    val passwordError: List<String>? = null,
    val repeatedPassword: String = "",
    val repeatedPasswordError: List<String>? = null,
    val disconnectOtherDevices: Boolean = false
)
