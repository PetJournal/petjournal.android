package com.soujunior.petjournal.ui.screensApp.accountmanager.changePasswordScreen

data class ChangePasswordFormState(
    var password: String = "",
    val passwordError: List<String>? = null,
    var repeatedPassword: String = "",
    val repeatedPasswordError: List<String>? = null,
    val disconnectOtherDevices: Boolean = false,
)
