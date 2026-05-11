package com.soujunior.domain.model.request

data class LoginPreferenceModel(
    val email: String,
    val password: String,
    val isRemember: Boolean
)
