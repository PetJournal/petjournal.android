package com.soujunior.domain.model.response.pet

import com.soujunior.domain.model.response.UserInfoResponse
import com.google.gson.annotations.SerializedName
import com.soujunior.domain.model.BreedDTO
import com.soujunior.domain.model.SizeDTO

data class PetDTO(
    @SerializedName("id")
    val id: String? = null,

    @SerializedName("guardianId")
    val guardianId: String? = null,

    @SerializedName("guardian")
    val guardian: UserInfoResponse? = null,

    @SerializedName("specie")
    val specie: Specie? = null,

    @SerializedName("specieAlias")
    val specieAlias: String? = null,

    @SerializedName("petName")
    val petName: String? = null,

    @SerializedName("gender")
    val gender: Char? = null,

    @SerializedName("breedAlias")
    val breedAlias: String? = null,

    @SerializedName("breed")
    val breedModel: BreedDTO? = null,

    @SerializedName("size")
    val sizeModel: SizeDTO? = null,

    @SerializedName("castrated")
    val castrated: Boolean? = null,

    @SerializedName("image")
    val petImage: String? = null,

    @SerializedName("dateOfBirth")
    val dateOfBirth: String? = null
)