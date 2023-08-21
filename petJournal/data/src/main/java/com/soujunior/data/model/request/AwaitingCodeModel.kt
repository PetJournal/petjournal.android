package com.soujunior.data.model.request

data class AwaitingCodeModel(
    val email: String,
    val verificationToken: String
)