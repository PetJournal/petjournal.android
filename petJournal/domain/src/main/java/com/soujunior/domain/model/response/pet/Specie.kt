package com.soujunior.domain.model.response.pet

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = false)
data class Specie(
    @Json(name = "id") val id: String? = null,
    @Json(name = "name") val name: String? = null
)