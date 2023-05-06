package com.soujunior.domain.entities.auth

data class AwaitingCodeModel(
    val codeOTP: String,
    val email: String
)
