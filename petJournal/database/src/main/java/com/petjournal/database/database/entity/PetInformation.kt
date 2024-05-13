package com.petjournal.database.database.entity

import androidx.room.Entity


@Entity(tableName = "pet_information")
data class PetInformation(
    val name: String? = null,
    val gender: String? = null,
    val size: String? = null,
    val petRace: String? = null,
    val petAge: String? = null
)
