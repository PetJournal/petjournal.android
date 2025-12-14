package com.soujunior.petjournal.ui.screensApp.accountmanager.awaitingCodeScreen

data class AwaitingCodeFormState(
    val codeOTP: String = "",
    val codeOTPError: List<String>? = null,
    val email: String = "",
)
