package com.soujunior.petjournal.ui.appArea.pets.petNameAndGenderScreen

/**
 * Classe responsável por controlar os eventos (entrada de dados, botões, etc) na tela NameAndGender
 * */
sealed class NameGenderFormEvent{
    data class PetName(val petName: String) : NameGenderFormEvent()
    data class PetGender (val petGender: String) : NameGenderFormEvent()

    object returnButton : NameGenderFormEvent()

    object nextButton : NameGenderFormEvent()

}
