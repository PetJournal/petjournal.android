package com.soujunior.domain.model

data class PetModel(
    val id: Long = 0,
    val idPet: String? = null,
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