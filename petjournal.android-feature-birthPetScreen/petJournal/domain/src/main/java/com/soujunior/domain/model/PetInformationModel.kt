package com.soujunior.domain.model

data class PetInformationModel(
    val id: Long,
    val species: String? = null,
    val name: String? = null,
    val gender: String? = null,
    val size: String? = null,
    val petRace: String? = null,
    val petAge: String? = null,
    val guardianId: Int? = null
)