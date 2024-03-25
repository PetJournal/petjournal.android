package com.soujunior.petjournal.ui.appArea.pets.petBirthScreen

/**
 * Classe responsável por controlar os eventos (entrada de dados, botões, etc) na tela NameAndGender
 * */
sealed class BirthFormEvent{
    data class PetName(val petName: String) : BirthFormEvent()
    data class PetGender (val petGender: String) : BirthFormEvent()

    object ReturnButton : BirthFormEvent()

    object NextButton : BirthFormEvent()

}
