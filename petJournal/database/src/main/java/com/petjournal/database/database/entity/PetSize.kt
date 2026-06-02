package com.petjournal.database.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "list_pet_sizes")
data class PetSize(
    @PrimaryKey val id: String = "",
    val name: String = "",
    val specieId: String = "",
    val tag: String = ""
)
