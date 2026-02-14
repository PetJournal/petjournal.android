package com.soujunior.domain.model

import com.google.gson.annotations.SerializedName
import java.time.LocalDate
import java.time.Period
import java.time.ZonedDateTime


data class PetDetailsDTO(
    @SerializedName("id")
    val id: String? = null,

    @SerializedName("guardianId")
    val guardianId: String? = null,

    @SerializedName("specie")
    val specie: SpecieDTO? = null,

    @SerializedName("specieAlias")
    val specieAlias: String? = null,

    @SerializedName("petName")
    val petName: String? = null,

    @SerializedName("gender")
    val gender: String? = null,

    @SerializedName("breedAlias")
    val breedAlias: String? = null,

    @SerializedName("breed")
    val breed: BreedDTO? = null,

    @SerializedName("size")
    val size: SizeDTO? = null,

    @SerializedName("castrated")
    val castrated: Boolean? = null,

    @SerializedName("dateOfBirth")
    val dateOfBirth: String? = null,

    @SerializedName("image")
    val image: String? = null
)

data class SpecieDTO(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("name")
    val name: String? = null
)

data class BreedDTO(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("name")
    val name: String? = null
)

data class SizeDTO(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("name")
    val name: String? = null
)

fun PetDetailsDTO.toPetModel(): PetModel {
    return PetModel(
        id = this.id?.toLongOrNull() ?: 0L,
        species = this.specieAlias ?: this.specie?.name,
        name = this.petName,
        gender = this.gender,
        size = this.size?.name,
        petRace = this.breedAlias ?: this.breed?.name,
        petAge = this.dateOfBirth?.let { parseAndCalculateAge(it) },
        guardianId = this.guardianId?.toIntOrNull(),
        castrated = this.castrated
    )
}


private fun parseAndCalculateAge(dateString: String): String {
    return try {
        val birthDate = ZonedDateTime.parse(dateString).toLocalDate()
        val currentDate = LocalDate.now()
        val period = Period.between(birthDate, currentDate)

        when {
            period.years > 1 -> "${period.years} anos"
            period.years == 1 -> "1 ano"
            period.months > 1 -> "${period.months} meses"
            period.months == 1 -> "1 mês"
            else -> "Menos de 1 mês"
        }
    } catch (e: Exception) {
        e.message.toString()
    }
}

fun List<PetDetailsDTO>.toDomain(): List<PetModel> {
    return this.map { it.toPetModel() }
}
