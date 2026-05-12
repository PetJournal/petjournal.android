package com.soujunior.domain.model.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = false)
data class PetRaceItemModel(
    @Json(name = "id") val id: String,
    @Json(name = "name") val name: String,
    @Json(name = "specieId") val specieId: String
)
