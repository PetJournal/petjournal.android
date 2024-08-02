package com.soujunior.petjournal.ui.screens_app.screens_pets.registeredPetScreen

import com.soujunior.domain.model.PetInformationModel
import com.soujunior.domain.model.response.PetInformationResponse
import com.soujunior.domain.model.response.pet_information.PetInformationItem

data class RegisteredPetFormState (
    val registeredPetList : List<PetInformationItem> = emptyList(),
)