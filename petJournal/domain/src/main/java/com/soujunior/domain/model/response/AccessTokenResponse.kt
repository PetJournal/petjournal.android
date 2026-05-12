package com.soujunior.domain.model.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = false)
data class AccessTokenResponse(
    @Json(name = "accessToken") val accessToken: String
)
