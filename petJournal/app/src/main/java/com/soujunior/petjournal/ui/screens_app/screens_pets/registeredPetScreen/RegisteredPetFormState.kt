package com.soujunior.petjournal.ui.screens_app.screens_pets.registeredPetScreen

import com.soujunior.domain.model.PetInformationModel

data class RegisteredPetFormState (
    val registeredPetList : List<PetInformationModel> = emptyList(),
)