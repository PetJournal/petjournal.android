package com.petjournal.database.converter

import com.petjournal.database.database.entity.PetInformation
import com.petjournal.database.database.entity.PetRace
import com.petjournal.database.database.entity.PetSize
import com.soujunior.domain.model.PetInformationModel
import com.soujunior.domain.model.request.PetRaceItemModel
import com.soujunior.domain.model.request.PetSizeItemModel
import com.soujunior.domain.model.response.PetInformationResponse

object Converter {
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
    fun PetInformationModel.toResponse(): PetInformationResponse {
        return PetInformationResponse(
            specieName = this.species,
            petName = this.name,
            gender = this.gender,
            breedName = this.petRace,
            size = this.size,
            castrated = this.castrated,
            dateOfBirth = this.petAge
        )
    }


    fun List<PetSize>.toListPetSizeItemModel(): List<PetSizeItemModel> {
        val listPetSizesItemModel: MutableList<PetSizeItemModel> = mutableListOf()
        this.forEach { petSize ->

            listPetSizesItemModel.add(
                PetSizeItemModel(
                    id = petSize.id,
                    name = petSize.name,
                    specieId = petSize.specieId
                )
            )
        }
        return listPetSizesItemModel
    }

    fun List<PetSizeItemModel>.toListPetSizeEntity(tag: String): List<PetSize> {
        val listPetSizesEntity: MutableList<PetSize> = mutableListOf()
        this.forEach { petSizeItemModel ->

            listPetSizesEntity.add(
                PetSize(
                    id = petSizeItemModel.id,
                    name = petSizeItemModel.name,
                    specieId = petSizeItemModel.specieId,
                    tag = tag

                )
            )
        }
        return listPetSizesEntity
    }

    fun List<PetRace>.toListPetRaceModel(): List<PetRaceItemModel> {
        val listPetRacesItemModel: MutableList<PetRaceItemModel> = mutableListOf()
        this.forEach { petRace ->
            listPetRacesItemModel.add(
                PetRaceItemModel(
                    id = petRace.id,
                    name = petRace.name,
                    specieId = petRace.specieId
                )
            )
        }
        return listPetRacesItemModel
    }

    fun List<PetRaceItemModel>.toListPetRaceEntity(tag: String): List<PetRace> {
        val listPetRacesEntity: MutableList<PetRace> = mutableListOf()
        this.forEach { petRaceItemModel ->

            listPetRacesEntity.add(
                PetRace(
                    id = petRaceItemModel.id,
                    name = petRaceItemModel.name,
                    specieId = petRaceItemModel.specieId, tag = tag
                )
            )
        }
        return listPetRacesEntity
    }
}