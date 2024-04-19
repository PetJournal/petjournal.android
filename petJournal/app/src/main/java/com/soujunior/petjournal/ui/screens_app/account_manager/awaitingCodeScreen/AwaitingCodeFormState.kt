package com.soujunior.petjournal.ui.screens_app.account_manager.awaitingCodeScreen

data class AwaitingCodeFormState(
    val codeOTP: String = "",
    val codeOTPError: List<String>? = null,
    val email: String = ""
)
