package com.soujunior.petjournal.ui.accountManager.awaitingCodeScreen

data class AwaitingCodeFormState(
    val codeOTP: String = "",
    val codeOTPError: List<String>? = null,
)
