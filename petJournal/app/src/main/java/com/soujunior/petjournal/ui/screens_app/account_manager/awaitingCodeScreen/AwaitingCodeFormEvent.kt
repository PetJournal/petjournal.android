package com.soujunior.petjournal.ui.screens_app.account_manager.awaitingCodeScreen

sealed class AwaitingCodeFormEvent {
    data class CodeOTPChanged (val code: String) : com.soujunior.petjournal.ui.screens_app.account_manager.awaitingCodeScreen.AwaitingCodeFormEvent()
    data class EmailChanged (val email: String) : com.soujunior.petjournal.ui.screens_app.account_manager.awaitingCodeScreen.AwaitingCodeFormEvent()
    object Submit : com.soujunior.petjournal.ui.screens_app.account_manager.awaitingCodeScreen.AwaitingCodeFormEvent()
    object ResendCode : com.soujunior.petjournal.ui.screens_app.account_manager.awaitingCodeScreen.AwaitingCodeFormEvent()
}