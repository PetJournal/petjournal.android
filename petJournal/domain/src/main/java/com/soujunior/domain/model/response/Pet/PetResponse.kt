package com.soujunior.domain.model.response.Pet

import com.soujunior.domain.model.response.UserInfoResponse
import java.time.LocalDate

data class PetResponse(
    val id: String? = null,
    val guardianId: String? = null,
    val guardian: UserInfoResponse? = null,
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

//todo: remover essa lista quando nao estiver mais sendo usada
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
