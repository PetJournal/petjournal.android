package com.soujunior.petjournal.ui.appArea.pets.petBirthDateScreen

/**
 * Classe responsável por controlar os eventos (entrada de dados, botões, etc) na tela NameAndGender
 * */
sealed class BirthDateFormEvent{
    data class PetBirthDate(val petBirth: String) : BirthDateFormEvent()
    //object ReturnButton : BirthFormEvent()

    object NextButton : BirthDateFormEvent()

    data class IdPetInformation(val idPetInformation: Long): BirthDateFormEvent()

}
