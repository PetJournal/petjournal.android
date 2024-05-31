package com.petjournal.database.converter

import com.petjournal.database.database.entity.ListPetRaces
import com.petjournal.database.database.entity.ListPetSizes
import com.petjournal.database.database.entity.PetInformation
import com.soujunior.domain.model.PetInformationModel
import com.soujunior.domain.model.request.PetRaceItemModel
import com.soujunior.domain.model.request.PetSizeItemModel

object Converter{
    fun PetInformation.toModel(): PetInformationModel {
        return PetInformationModel(
            id = this.id,
            species = this.species,
            name = this.name,
            gender = this.gender,
            size = this.size,
            petRace = this.petRace,
            petAge = this.petAge,
            guardianId = this.guardianId
        )
    }

    fun PetInformationModel.toEntity(): PetInformation {
        return PetInformation(
            id = this.id,
            guardianId = this.guardianId ?: 0,
            species = this.species,
            name = this.name,
            gender = this.gender,
            size = this.size,
            petRace = this.petRace,
            petAge = this.petAge
        )
    }
    fun ListPetSizes.toListPetSizeItemModel(): List<PetSizeItemModel> {
        val listPetSizesItemModel: MutableList<PetSizeItemModel> = mutableListOf()
        this.listPetSizes?.forEach {
            listPetSizesItemModel.add(it)
        }
        return listPetSizesItemModel
    }
    fun List<PetSizeItemModel>.toListPetSizeEntity(id: String): ListPetSizes {
        return ListPetSizes(
            id = id,
            listPetSizes = this
        )
    }

    fun ListPetRaces.toListPetRaceItemModel(): List<PetRaceItemModel> {
        val listPetRacesItemModel: MutableList<PetRaceItemModel> = mutableListOf()
        this.listPetRaces?.forEach {
            listPetRacesItemModel.add(it)
        }
        return listPetRacesItemModel
    }
    fun List<PetRaceItemModel>.toListPetRaceEntity(id: String): ListPetRaces {
        return ListPetRaces(
            id = id,
            listPetRaces = this
        )
    }
}