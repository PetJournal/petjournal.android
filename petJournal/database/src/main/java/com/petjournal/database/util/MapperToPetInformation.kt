package com.petjournal.database.util

import com.petjournal.database.database.entity.PetInformation
import com.soujunior.domain.model.PetInformationModel

fun List<PetInformationModel>.toPetInformationList(): List<PetInformation> {
    return this.map { model ->
        PetInformation(
            idLocal = null,
            idApi = model.id,
            guardianId = "0",
            species = model.species,
            name = model.name,
            gender = model.gender,
            petRace = model.petRace,
            size = model.size,
            castration = model.castration,
            petAge = model.petAge
        )
    }
}

fun PetInformation.toPetInformationModel(): PetInformationModel {
    return PetInformationModel(
//        idLocal = this.idLocal,
        id = this.idApi,
        species = this.species,
        name = this.name,
        gender = this.gender,
        size = this.size,
        petRace = this.petRace,
        petAge = this.petAge,
        guardianId = this.guardianId,
        castration = this.castration
    )
}
