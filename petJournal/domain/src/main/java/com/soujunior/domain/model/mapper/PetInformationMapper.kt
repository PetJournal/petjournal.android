package com.soujunior.domain.model.mapper

import com.soujunior.domain.model.PetInformationModel
import com.soujunior.domain.model.response.PetInformationRequest

fun PetInformationRequest.toPetInformationModel(id: String, guardianId: String? = null): PetInformationModel {
    return PetInformationModel(
        id = this.id,
        species = this.specieName,
        name = this.petName,
        gender = this.gender,
        size = this.size,
        petRace = this.breedName,
        petAge = this.dateOfBirth,
        guardianId = guardianId,
        castration = this.castrated
    )
}

