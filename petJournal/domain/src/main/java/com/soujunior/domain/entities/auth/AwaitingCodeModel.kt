package com.soujunior.domain.entities.auth

//TODO: Adicionar email
data class AwaitingCodeModel(
    val codeOTP: String,
    val email: String
)
