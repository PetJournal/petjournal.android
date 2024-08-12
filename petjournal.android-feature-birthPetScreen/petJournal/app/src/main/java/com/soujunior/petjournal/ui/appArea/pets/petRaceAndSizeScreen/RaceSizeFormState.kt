package com.soujunior.petjournal.ui.appArea.pets.petRaceAndSizeScreen

data class RaceSizeFormState(
    val race: String = "",
    val size: String = "",
    val raceOthers: String = "",
    val raceError: List<String>? = null,
    val sizeError: List<String>? = null,
    val raceOthersError: List<String>? = null,
    val specie: String = "",
    val idPetInformation : Long? = null,
    val name: String = "",
    val gender: String = "",
)

