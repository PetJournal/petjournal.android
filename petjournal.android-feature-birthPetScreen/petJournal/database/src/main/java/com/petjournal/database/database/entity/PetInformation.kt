package com.petjournal.database.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "pet_information",
    foreignKeys = [ForeignKey(
        entity = GuardianProfile::class,
        parentColumns = ["id"],
        childColumns = ["guardianId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("guardianId")]
)
data class PetInformation(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val guardianId: Int,
    val species: String? = null,
    val name: String? = null,
    val gender: String? = null,
    val size: String? = null,
    val petRace: String? = null,
    val petAge: String? = null,
)


