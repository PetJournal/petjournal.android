package com.soujunior.petjournal.ui.screens_app.screens_pets.petNameAndGenderScreen

/**
 * Classe responsável por controlar os eventos (entrada de dados, botões, etc) na tela NameAndGender
 * */
sealed class NameGenderFormEvent {
    data class PetName(val petName: String) : NameGenderFormEvent()
    data class PetGender(val petGender: String) : NameGenderFormEvent()
    data class IdPetInformation(val idPetInformation: Long) : NameGenderFormEvent()
    object ReturnButton : NameGenderFormEvent()
    object NextButton : NameGenderFormEvent()
}
