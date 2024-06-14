package com.soujunior.petjournal.ui.appArea.pets.registeredPetScreen

import com.soujunior.domain.model.PetInformationModel

data class RegisteredPetFormState (
    val registeredPetList : List<PetInformationModel> = emptyList()
)