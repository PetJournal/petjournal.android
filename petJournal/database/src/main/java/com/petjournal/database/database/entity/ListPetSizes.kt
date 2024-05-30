package com.petjournal.database.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.soujunior.domain.model.request.PetSizeItemModel

@Entity(tableName = "list_pet_sizes")
data class ListPetSizes(
    @PrimaryKey val id: String = "",
    val listPetSizes: List<PetSizeItemModel>? = emptyList(),
)
