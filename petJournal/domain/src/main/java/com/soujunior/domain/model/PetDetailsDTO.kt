package com.soujunior.domain.model

import com.google.gson.annotations.SerializedName
import java.time.LocalDate
import java.time.Period
import java.time.ZonedDateTime
import java.time.format.DateTimeParseException

data class PetDetailsDTO(
    @SerializedName("id")
    val id: String? = null,

    @SerializedName("guardianId")
    val guardianId: String? = null,

    @SerializedName("specie")
    val specie: SpecieDTO? = null,

    @SerializedName("specieAlias")
    val specieAlias: String? = null,

    @SerializedName("petName")
    val petName: String? = null,

    @SerializedName("gender")
    val gender: String? = null,

    @SerializedName("breedAlias")
    val breedAlias: String? = null,

    @SerializedName("breed")
    val breed: BreedDTO? = null,

    @SerializedName("size")
    val size: SizeDTO? = null,

    @SerializedName("castrated")
    val castrated: Boolean? = null,

    @SerializedName("dateOfBirth")
    val dateOfBirth: String? = null,

    @SerializedName("image")
    val image: String? = null
)

data class SpecieDTO(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("name")
    val name: String? = null
)

data class BreedDTO(
    @SerializedName("id") val id: String? = null,
    @SerializedName("specieId") val specieId: String? = null,
    @SerializedName("name") val name: String? = null
)

data class SizeDTO(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("name")
    val name: String? = null
)

data class PetCreateDTO(
    @SerializedName("specieName") val specieName: String? = null,
    @SerializedName("petName") val petName: String? = null,
    @SerializedName("gender") val gender: String? = null,
    @SerializedName("breedName") val breedName: String? = null,
    @SerializedName("size") val size: String? = null,
    @SerializedName("castrated") val castrated: Boolean? = null,
    @SerializedName("dateOfBirth") val dateOfBirth: String? = null,
    @SerializedName("image") val image: String? = null
)
