package com.petjournal.database.converter

import com.petjournal.database.database.entity.PetInformation
import com.soujunior.domain.model.PetInformationModel

object Converter{
    fun PetInformation.toModel(): PetInformationModel {
        return PetInformationModel(
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
            guardianId = this.guardianId ?: 0,
            species = this.species,
            name = this.name,
            gender = this.gender,
            size = this.size,
            petRace = this.petRace,
            petAge = this.petAge
        )
    }
}