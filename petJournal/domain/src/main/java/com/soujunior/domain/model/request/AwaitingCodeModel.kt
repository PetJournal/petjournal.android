package com.soujunior.domain.model.request

data class AwaitingCodeModel(
    val email: String,
    val verificationToken: String
)