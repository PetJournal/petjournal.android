package com.soujunior.domain.model.response

// TODO: Trocar para petInformationRequest ou PetInformationRequestBody
data class PetInformationRequest(
    val id: String,
    val specieName: String? = null,
    val petName: String? = null,
    val gender: String? = null,
    val breedName: String? = null,
    val size: String? = null,
    val castrated: Boolean? = null,
    val dateOfBirth: String? = null,
)