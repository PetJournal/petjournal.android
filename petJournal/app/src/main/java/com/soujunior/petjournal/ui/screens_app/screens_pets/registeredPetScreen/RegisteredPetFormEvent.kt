package com.soujunior.petjournal.ui.appArea.pets.registeredPetScreen


sealed class RegisteredPetFormEvent {
    object SelectButton : RegisteredPetFormEvent()
//    data class IdPetInformation(val idPetInformation: Long) : RaceSizeFormEvent()

}