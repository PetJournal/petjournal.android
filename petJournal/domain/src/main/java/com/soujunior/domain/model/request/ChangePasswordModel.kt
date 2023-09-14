package com.soujunior.domain.model.request

data class ChangePasswordModel(
    val password: String,
    val passwordConfirmation: String
)