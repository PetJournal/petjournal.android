package com.soujunior.domain.model.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = false)
data class SignUpModel(
    @Json(name = "firstName") val firstName: String,
    @Json(name = "lastName") val lastName: String,
    @Json(name = "email") val email: String,
    @Json(name = "password") val password: String,
    @Json(name = "passwordConfirmation") val passwordConfirmation: String,
    @Json(name = "phone") val phone: String,
    @Json(name = "isPrivacyPolicyAccepted") val isPrivacyPolicyAccepted: Boolean,
)
