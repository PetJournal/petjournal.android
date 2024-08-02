package com.soujunior.data.util

import com.soujunior.domain.model.PetInformationModel
import com.soujunior.domain.model.response.pet_information.Breed
import com.soujunior.domain.model.response.pet_information.PetInformationItem
import com.soujunior.domain.model.response.pet_information.Size
import com.soujunior.domain.model.response.pet_information.Specie

fun List<PetInformationModel>.toPetInformationItemList(): List<PetInformationItem> {
    return this.map { model ->
        PetInformationItem(
            id = model.id.toString(),
            guardianId = model.guardianId?.toString(),
            specie = model.species?.let { Specie(id = "", name = it) }, // Specie ID needs to be assigned properly
            specieAlias = null,
            petName = model.name,
            gender = model.gender,
            breedAlias = model.petRace,
            breed = model.petRace?.let { Breed(id = "", name = it) }, // Breed ID needs to be assigned properly
            size = model.size?.let { Size(id = "", name = it) }, // Size ID needs to be assigned properly
            castrated = model.castrated,
            dateOfBirth = model.petAge // This needs to be properly converted to a date format if necessary
        )
    }
}