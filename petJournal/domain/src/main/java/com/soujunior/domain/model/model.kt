package com.soujunior.domain.model

import com.google.gson.annotations.SerializedName
import java.time.LocalDate
import java.time.Period
import java.time.ZonedDateTime


data class PetResponseNew(
    @SerializedName("id")
    val id: String? = null,

    @SerializedName("guardianId")
    val guardianId: String? = null,

    @SerializedName("specie")
    val specie: SpecieResponseNew? = null,

    @SerializedName("specieAlias")
    val specieAlias: String? = null,

    @SerializedName("petName")
    val petName: String? = null,

    @SerializedName("gender")
    val gender: String? = null,

    @SerializedName("breedAlias")
    val breedAlias: String? = null,

    @SerializedName("breed")
    val breed: BreedResponseNew? = null,

    @SerializedName("size")
    val size: SizeResponseNew? = null,

    @SerializedName("castrated")
    val castrated: Boolean? = null,

    @SerializedName("dateOfBirth")
    val dateOfBirth: String? = null,

    @SerializedName("image")
    val image: String? = null
)

data class SpecieResponseNew(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("name")
    val name: String? = null
)

data class BreedResponseNew(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("name")
    val name: String? = null
)

data class SizeResponseNew(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("name")
    val name: String? = null
)

fun PetResponseNew.toPetModel(): PetModel {
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
        "Data inválida"
    }
}

fun List<PetResponseNew>.toDomain(): List<PetModel> {
    return this.map { it.toPetModel() }
}