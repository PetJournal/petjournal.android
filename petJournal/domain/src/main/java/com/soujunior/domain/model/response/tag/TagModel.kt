package com.soujunior.domain.model.response.tag

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = false)
data class TagModel(
    @Json(name = "id") val id: String? = null,
    @Json(name = "guardianId") val guardianId: String? = null,
    @Json(name = "name") val name: String? = null,
    @Json(name = "color") val color: String? = null,
)