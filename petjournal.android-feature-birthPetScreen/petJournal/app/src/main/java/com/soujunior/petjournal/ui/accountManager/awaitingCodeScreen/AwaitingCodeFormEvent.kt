package com.soujunior.petjournal.ui.accountManager.awaitingCodeScreen

sealed class AwaitingCodeFormEvent {
    data class CodeOTPChanged (val code: String) : AwaitingCodeFormEvent()
    data class EmailChanged (val email: String) : AwaitingCodeFormEvent()
    object Submit : AwaitingCodeFormEvent()
    object ResendCode : AwaitingCodeFormEvent()
}