package com.soujunior.domain.model.response

data class UserInfoResponse(
    val id: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phone: String
)