package com.soujunior.petjournal.ui.appArea.pets.speciesChoiceScreen

sealed class PetFormEvent {
    data class SpecieChosen(val specieSelected: String) : PetFormEvent()
    data class OtherSpecie(val specieWritten: String) : PetFormEvent()
    object OtherButton : PetFormEvent()
    object ReturnButton : PetFormEvent()
    object NextButton : PetFormEvent()
}
