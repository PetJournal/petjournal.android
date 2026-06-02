package com.soujunior.petjournal.ui.screensapp.accountmanager.awaitingCodeScreen

data class AwaitingCodeFormState(
    val codeOTP: String = "",
    val codeOTPError: List<String>? = null,
    val email: String = "",
)
