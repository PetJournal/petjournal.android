package com.soujunior.petjournal.ui.appArea.speciesChoiceScreen

/*
* Imagino que esse dataclass será o formulário completo que vai ser enviado para o banco*/
data class PetFormState(
    val specie: String = "",
    val specieError: List<String>? = null
)