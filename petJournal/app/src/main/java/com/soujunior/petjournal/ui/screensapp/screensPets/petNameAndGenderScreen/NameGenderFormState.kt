package com.soujunior.petjournal.ui.screensapp.screensPets.petNameAndGenderScreen

data class NameGenderFormState(
    val name: String = "",
    val gender: String = "",
    val nameError: List<String>? = null,
    val genderError: List<String>? = null,
    val specie: String = "",
    val idPetInformation: Long? = null,
)
