package com.soujunior.domain.model.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = false)
data class ChangePasswordModel(
    @Json(name = "password") val password: String,
    @Json(name = "passwordConfirmation") val passwordConfirmation: String
)
