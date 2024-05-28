package com.soujunior.petjournal.ui.appArea.pets.registeredPetScreen

import com.soujunior.petjournal.ui.appArea.pets.petRaceAndSizeScreen.RaceSizeFormEvent

sealed class RegisteredPetFormEvent {

    object SelectButton : RegisteredPetFormEvent()
//    data class IdPetInformation(val idPetInformation: Long) : RaceSizeFormEvent()

}