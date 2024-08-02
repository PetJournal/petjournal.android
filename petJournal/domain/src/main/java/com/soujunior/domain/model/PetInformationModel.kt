package com.soujunior.domain.model

import java.time.LocalDate

data class PetInformationModel(
    val id: Long,
    val species: String? = null,
    val name: String? = null,
    val gender: String? = null,
    val size: String? = null,
    val petRace: String? = null,
    val petAge: String? = null,
    val guardianId: Int? = null,
    val castrated: Boolean? = null
)