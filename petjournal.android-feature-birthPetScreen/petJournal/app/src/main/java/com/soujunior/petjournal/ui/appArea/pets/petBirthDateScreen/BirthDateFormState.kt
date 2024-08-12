package com.soujunior.petjournal.ui.appArea.pets.petBirthDateScreen

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

