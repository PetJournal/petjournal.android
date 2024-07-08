package com.soujunior.petjournal.ui.screens_app.screens_pets.petBirthDateScreen

/**
 * Classe responsável por controlar os eventos (entrada de dados, botões, etc) na tela NameAndGender
 * */
sealed class BirthDateFormEvent {
    data class PetBirthDate(val petBirth: String) : BirthDateFormEvent()
    object NextButton : BirthDateFormEvent()
    data class IdPetInformation(val idPetInformation: Long) : BirthDateFormEvent()
    data class PetCastration(val petCastration: Boolean?) : BirthDateFormEvent()
}
