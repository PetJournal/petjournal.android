package com.soujunior.petjournal.ui.screens_app.screens_pets.speciesChoiceScreen

data class PetFormState(
    val specie: String = "",
    val specieError: List<String>? = null,
    val name: String? = null,
    val idRoomPetInformation: Long? = null,
    val message: String? = null
)
