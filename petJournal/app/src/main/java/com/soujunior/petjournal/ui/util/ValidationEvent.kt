package com.soujunior.petjournal.ui.util

sealed class ValidationEvent {
    object Success : ValidationEvent()
    object Failed : ValidationEvent()
}