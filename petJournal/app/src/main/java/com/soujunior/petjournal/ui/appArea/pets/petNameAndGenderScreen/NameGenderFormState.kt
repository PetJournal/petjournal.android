package com.soujunior.petjournal.ui.appArea.pets.petNameAndGenderScreen

data class NameGenderFormState(
    val name: String = "",
    val gender: Char? = null,
    val nameError: List<String>? = null,
    val genderError: List<String>? = null
)

