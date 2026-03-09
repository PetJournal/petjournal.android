package com.soujunior.domain.model

data class PetFormRequest(
    val specieName: String,
    val petName: String,
    val breedName: String,
    val size: String,
    val dateOfBirth: String,
    val gender: String,
    val castrated: Boolean,
    val imagePath: String?
)