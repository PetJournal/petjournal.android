package com.soujunior.petjournal.ui.accountManager.loginScreen

sealed class ValidationEvent {
    object Success : ValidationEvent()
    object Failed : ValidationEvent()
}