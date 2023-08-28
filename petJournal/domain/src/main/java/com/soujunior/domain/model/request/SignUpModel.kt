package com.soujunior.domain.model.request

data class SignUpModel(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val passwordConfirmation: String,
    val phone: String,
    val isPrivacyPolicyAccepted: Boolean,
)