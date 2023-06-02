package com.soujunior.petjournal.ui.accountManager.awaitingCodeScreen

data class AwaitingCodeFormState(
    val email: String = "",
    val emailError: List<String>? = null,
    val codeOTP: String = "",
)
