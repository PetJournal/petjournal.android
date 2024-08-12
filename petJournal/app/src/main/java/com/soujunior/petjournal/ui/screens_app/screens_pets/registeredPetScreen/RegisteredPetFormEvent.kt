package com.soujunior.petjournal.ui.appArea.pets.registeredPetScreen

import com.soujunior.domain.model.PetInformationModel


sealed class RegisteredPetFormEvent {
    object SelectButton : RegisteredPetFormEvent()

}