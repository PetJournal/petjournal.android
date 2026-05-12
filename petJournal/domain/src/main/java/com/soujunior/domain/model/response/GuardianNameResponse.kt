package com.soujunior.domain.model.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = false)
data class GuardianNameResponse(
    @Json(name = "firstName") val firstName: String,
    @Json(name = "lastName") val lastName: String
)
