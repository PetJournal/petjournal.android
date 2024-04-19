package com.soujunior.petjournal.ui.screens_app.account_manager.registerScreen

sealed class RegisterFormEvent {
    data class NameChanged(val name: String) : RegisterFormEvent()
    data class LastNameChanged(val lastName: String) : RegisterFormEvent()
    data class EmailChanged(val email: String) : RegisterFormEvent()
    data class PhoneChanged(val phone: String) : RegisterFormEvent()
    data class PasswordChanged(val password: String) : RegisterFormEvent()
    data class ConfirmPasswordChanged(val confirmPassword: String) : RegisterFormEvent()
    data class PrivacyPolicyChanged(val privacyPolicy: Boolean) : RegisterFormEvent()
    object Submit : RegisterFormEvent()
}