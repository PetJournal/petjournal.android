package com.soujunior.petjournal.ui

sealed class ValidationEvent {
    object Success : ValidationEvent()
    object Failed : ValidationEvent()
}