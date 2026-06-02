package com.petjournal.database.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.soujunior.domain.model.BreedDTO
import com.soujunior.domain.model.SizeDTO
import com.soujunior.domain.model.SpecieDTO

@Entity(tableName = "pet_details")
data class PetDetailsEntity(
    @PrimaryKey
    val id: String,
    val guardianId: String?,
    val specie: SpecieDTO?,
    val specieAlias: String?,
    val petName: String?,
    val gender: String?,
    val breedAlias: String?,
    val breed: BreedDTO?,
    val size: SizeDTO?,
    val castrated: Boolean?,
    val dateOfBirth: String?,
    val image: String?
)
