package com.soujunior.petjournal.ui.accountManager.awaitingCodeScreen

sealed class AwaitingCodeFormEvent {
    data class EmailChanged(val email: String) : AwaitingCodeFormEvent()
    data class CodeOTPChanged (val code: String) : AwaitingCodeFormEvent()
    object Submit : AwaitingCodeFormEvent()
}