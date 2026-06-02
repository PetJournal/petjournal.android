package com.soujunior.domain.model

data class PetModelV2(
    val id: String? = null,
    val petName: String? = null,
    val species: String? = null,
    val petRace: String? = null,
    val size: String? = null,
    val weight: String? = null,
    val dateOfBirth: String? = null,
    val gender: String? = null,
    val castrated: Boolean? = null,
    val image: String? = null,
    val guardianId: Int? = null,
    val petAge: String? = null
)