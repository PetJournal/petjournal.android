package com.soujunior.petjournal.ui.screens_app.screens_pets.petRaceAndSizeScreen

/**
 * Classe responsável por controlar os eventos (entrada de dados, botões, etc) na tela RaceAndSize
 * */
sealed class RaceSizeFormEvent {
    data class PetRace(val petRace: String) : RaceSizeFormEvent()
    data class PetRaceOthers(val petRaceOthers: String) : RaceSizeFormEvent()
    data class PetSize(val petSize: String) : RaceSizeFormEvent()
    data class IdPetInformation(val idPetInformation: Long) : RaceSizeFormEvent()
    object NextButton : RaceSizeFormEvent()
    object ReturnButton : RaceSizeFormEvent()
    data class ScrollToTop(val ScrollToTop: Boolean) : RaceSizeFormEvent()
}
