package com.soujunior.domain.entities.auth

data class RegisterModel(
    val email: String,
    val firstName: String,
    val isPrivacyPolicyAccepted: Boolean,
    val lastName: String,
    val password: String,
    val passwordConfirmation: String,
    val phone: String
)