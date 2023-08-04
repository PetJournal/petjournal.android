package com.soujunior.data.model.response

data class SignUpResponse(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phone: String
)