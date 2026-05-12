package com.soujunior.domain.model.response.pet

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = false)
data class BreedModel(
    @Json(name = "id") val id: String? = null,
    @Json(name = "specieId") val specieId: String? = null,
    @Json(name = "name") val name: String? = null
)