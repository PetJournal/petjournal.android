package com.soujunior.domain.model.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = false)
data class ForgotPasswordModel(
    @Json(name = "email") val email: String
)
