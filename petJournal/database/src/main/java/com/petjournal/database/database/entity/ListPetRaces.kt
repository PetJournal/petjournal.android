package com.petjournal.database.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.soujunior.domain.model.request.PetRaceItemModel
import com.soujunior.domain.model.request.PetSizeItemModel
/***
    O id será a espécie e terá uma lista de raças vinculada a ela.
 ***/
@Entity(tableName = "list_pet_races")
data class ListPetRaces(
    @PrimaryKey val id: String = "",
    val listPetRaces: List<PetRaceItemModel>? = emptyList(),
)
