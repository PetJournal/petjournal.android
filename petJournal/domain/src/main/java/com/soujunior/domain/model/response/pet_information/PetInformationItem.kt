package com.soujunior.domain.model.response.pet_information


data class PetInformationItem(
    val id: String? = null,
    val guardianId: String? = null,
    val specie: Specie? = null,
    val specieAlias: String? = null,
    val petName: String? = null,
    val gender: String? = null,
    val breedAlias: String? = null,
    val breed: Breed? = null,
    val size: Size? = null,
    val castrated: Boolean? = null,
    val dateOfBirth: String? = null
)