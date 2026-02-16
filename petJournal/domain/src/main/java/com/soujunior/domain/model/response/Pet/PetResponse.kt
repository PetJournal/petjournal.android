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