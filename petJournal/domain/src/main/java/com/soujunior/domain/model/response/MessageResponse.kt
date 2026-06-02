package com.soujunior.domain.model.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = false)
data class MessageResponse(
    @Json(name = "message") val message: String
)
