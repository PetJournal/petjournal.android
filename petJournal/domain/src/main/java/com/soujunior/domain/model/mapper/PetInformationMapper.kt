package com.soujunior.domain.model.mapper

import com.soujunior.domain.model.PetInformationModel
import com.soujunior.domain.model.response.PetInformationResponse

fun PetInformationResponse.toPetInformationModel(id: Long, guardianId: Int? = null): PetInformationModel {
    return PetInformationModel(
        id = id,
        species = this.specieName,
        name = this.petName,
        gender = this.gender,
        size = this.size,
        petRace = this.breedName,
        petAge = this.dateOfBirth,
        guardianId = guardianId,
        castrated = this.castrated
    )
}

