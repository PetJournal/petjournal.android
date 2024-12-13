package com.soujunior.data.util

import com.soujunior.domain.model.PetInformationModel
import com.soujunior.domain.model.response.pet_information.Breed
import com.soujunior.domain.model.response.pet_information.PetInformationItem
import com.soujunior.domain.model.response.pet_information.Size
import com.soujunior.domain.model.response.pet_information.Specie

fun List<PetInformationModel>.toPetInformationItemList(): List<PetInformationItem> {
    return this.map { model ->
        PetInformationItem(
            id = model.id,
            idApi = model.apiId,
            guardianId = model.guardianId,
            specie = model.species?.let { Specie(id = "", name = it) },
            specieAlias = null,
            petName = model.name,
            gender = model.gender,
            breedAlias = model.petRace,
            breed = model.petRace?.let { Breed(id = "", name = it) },
            size = model.size?.let { Size(id = "", name = it) },
            castrated = model.castration,
            dateOfBirth = model.petAge
        )
    }
}

fun List<PetInformationItem>.toPetInformationModelList(): List<PetInformationModel> {
    return this.map { model ->
        PetInformationModel(
            id = model.id,
            apiId = model.idApi,
            guardianId = model.guardianId,
            species = model.specie!!.name,
            name = model.petName,
            gender = model.gender,
            petRace = model.breed!!.name,
            size = model.size!!.name,
            castration = model.castrated,
            petAge = model.dateOfBirth
        )
    }
}