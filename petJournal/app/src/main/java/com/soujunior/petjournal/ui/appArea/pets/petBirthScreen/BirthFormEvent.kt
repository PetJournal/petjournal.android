package com.soujunior.petjournal.ui.appArea.pets.petBirthScreen

/**
 * Classe responsável por controlar os eventos (entrada de dados, botões, etc) na tela NameAndGender
 * */
sealed class BirthFormEvent{
    data class PetBirth(val petBirth: String) : BirthFormEvent()
    //object ReturnButton : BirthFormEvent()

    object NextButton : BirthFormEvent()

}
