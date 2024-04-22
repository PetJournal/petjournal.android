package com.soujunior.petjournal.ui.screens_app.pets_screens.petBirthDateScreen

data class BirthDateFormState(
    val birth: String = "",
    val birthError: List<String>? = null,
    val specie: String = "",
    val idPetInformation: Long? = null,
    val name: String = "",
    val gender: String = "",
    val size: String = "",
    val race: String = ""
)

