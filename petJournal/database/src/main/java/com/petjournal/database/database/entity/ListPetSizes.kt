package com.petjournal.database.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.soujunior.domain.model.request.PetSizeItemModel
/***
    O id será a espécie e terá uma lista de tamanhos vinculada a ela.
 ***/
@Entity(tableName = "list_pet_sizes")
data class ListPetSizes(
    @PrimaryKey val id: String = "",
    val listPetSizes: List<PetSizeItemModel>? = emptyList(),
)
