package com.soujunior.domain.model.response

import java.time.LocalDate

data class PetResponse(
    val id: String? = null,
    val guardianId: String? = null,
    val specie: Specie? = null,
    val specieAlias: String? = null,
    val petName: String? = null,
    val gender: Char? = null,
    val breedAlias: String? = null,
    val breed: Breed? = null,
    val size: Size? = null,
    val castrated: Boolean? = null,
    val petImage: String? = null,
    val dateOfBirth: LocalDate? = null
)

data class Specie(
    val id: String? = null,
    val name: String? = null
)

data class Breed(
    val id: String? = null,
    val name: String? = null
)

data class Size(
    val id: String? = null,
    val name: String? = null
)

val petList = listOf(
    PetResponse(
        petName = "Felicia Vascaina",
        petImage = "https://cdn.pixabay.com/photo/2023/08/18/15/02/dog-8198719_1280.jpg"
    ),
    PetResponse(
        petName = "Jujuba",
        petImage = "https://cdn.pixabay.com/photo/2023/08/18/15/02/dog-8198719_1280.jpg"
    ),
    PetResponse(
        petName = "Nutella",
        petImage = "https://cdn.pixabay.com/photo/2023/08/18/15/02/dog-8198719_1280.jpg"
    ),
    )
