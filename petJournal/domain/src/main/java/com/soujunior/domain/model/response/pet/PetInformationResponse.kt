package com.soujunior.domain.model.response.pet

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = false)
data class PetInformationResponse(
    @Json(name = "specieName") val specieName: String? = null,
    @Json(name = "petName") val petName: String? = null,
    @Json(name = "gender") val gender: String? = null,
    @Json(name = "breedName") val breedName: String? = null,
    @Json(name = "size") val size: String? = null,
    @Json(name = "castrated") val castrated: Boolean? = null,
    @Json(name = "dateOfBirth") val dateOfBirth: String? = null,
    @Json(name = "image") val image: String = ""
)
