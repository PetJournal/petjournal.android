package com.soujunior.domain.model.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = false)
data class LoginPreferenceModel(
    @Json(name = "email") val email: String,
    @Json(name = "password") val password: String,
    @Json(name = "isRemember") val isRemember: Boolean
)
