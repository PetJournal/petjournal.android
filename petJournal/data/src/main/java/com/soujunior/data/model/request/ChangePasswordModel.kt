package com.soujunior.data.model.request

data class ChangePasswordModel(
    val password: String,
    val passwordConfirmation: String
)