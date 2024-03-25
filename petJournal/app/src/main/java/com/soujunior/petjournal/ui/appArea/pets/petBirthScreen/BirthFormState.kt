package com.soujunior.petjournal.ui.appArea.pets.petBirthScreen

data class BirthFormState(
    val name: String = "",
    val gender: String = "",
    val nameError: List<String>? = null,
    val genderError: List<String>? = null
)

