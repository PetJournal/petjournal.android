package com.soujunior.domain.entities.auth

data class RegisterModel(
    val name: String,
    val lastName: String,
    val phoneNumber: String,
    val email: String,
    val password: String,
    val privacyPolicy : Boolean
)
