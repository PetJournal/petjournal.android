package com.soujunior.domain.model


data class PetInformationModel(
    val idLocal: Long? = null, //declarado como null para ser autoincrementado pelo room
    val id: String? = null,
    val species: String? = null,
    val name: String? = null,
    val gender: String? = null,
    val size: String? = null,
    val petRace: String? = null,
    val petAge: String? = null,
    val guardianId: String? = null,
    val castration: Boolean? = null
)