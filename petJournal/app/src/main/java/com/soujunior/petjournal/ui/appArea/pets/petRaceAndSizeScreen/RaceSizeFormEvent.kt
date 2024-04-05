package com.soujunior.petjournal.ui.appArea.pets.petRaceAndSizeScreen

/**
 * Classe responsável por controlar os eventos (entrada de dados, botões, etc) na tela RaceAndSize
 * */
sealed class RaceSizeFormEvent {
    data class PetRace(val petRace: String) : RaceSizeFormEvent()
    data class PetRaceOthers(val petRaceOthers: String) : RaceSizeFormEvent()
    data class PetSize(val petSize: String) : RaceSizeFormEvent()

    object ReturnButton : RaceSizeFormEvent()

    object NextButton : RaceSizeFormEvent()

    data class IdPetInformation(val idPetInformation: Long) : RaceSizeFormEvent()
}
