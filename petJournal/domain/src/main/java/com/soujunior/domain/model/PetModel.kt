package com.soujunior.domain.model

import java.time.LocalDate
import java.time.Period
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

data class PetModel(
    val id: Long = 0,
    val petName: String? = null,
    val species: String? = null,
    val petRace: String? = null,
    val size: String? = null,
    val weight: String? = null,
    val dateOfBirth: String? = null,
    val gender: String? = null,
    val castrated: Boolean? = null,
    val image: String? = null,
    val guardianId: Int? = null,
    val petAge: String? = null
)

data class PetFormRequest(
    val specieName: String,
    val petName: String,
    val breedName: String,
    val size: String,
    val dateOfBirth: String,
    val gender: String,
    val castrated: Boolean,
    val imagePath: String?
)

fun PetModel.toFormRequest(): PetFormRequest {
    return PetFormRequest(
        specieName = this.species ?: "",
        petName = this.petName ?: "",
        breedName = this.petRace ?: "",

        size = this.size ?: "",

        dateOfBirth = formatDateToBackend(this.dateOfBirth),

        gender = if (this.gender == "Macho") "M" else "F",
        castrated = this.castrated ?: false,
        imagePath = this.image
    )
}

fun PetModel.toDTO(): PetCreateDTO {
    return PetCreateDTO(
        specieName = this.species?.lowercase()?.replaceFirstChar { it.uppercase() } ?: "",
        petName = this.petName ?: "",
        gender = if (this.gender?.equals("M", ignoreCase = true) == true) "M" else "F",
        breedName = this.petRace ?: "",
        size = this.size ?: "",
        castrated = this.castrated ?: false,
        dateOfBirth = formatDateToBackend(this.dateOfBirth),
        image = this.image
    )
}

fun formatDateToBackend(dateOfBirth: String?): String {
    if (dateOfBirth.isNullOrBlank() || dateOfBirth.length < 8) return ""

    return try {
        val day = dateOfBirth.substring(0, 2)
        val month = dateOfBirth.substring(2, 4)
        val year = dateOfBirth.substring(4, 8)

        "${year}-${month}-${day}T00:00:00Z"
    } catch (e: Exception) {
        ""
    }
}

fun List<PetDetailsDTO>.toPetModelList(): List<PetModel> {
    return this.map { it.toPetModel() }
}

fun PetDetailsDTO.toPetModel(): PetModel {
    return PetModel(
        id = this.id?.toLongOrNull() ?: this.id?.hashCode()?.toLong() ?: 0L,
        species = this.specie?.name ?: this.specieAlias,
        petName = this.petName,
        gender = this.gender,
        size = this.size?.name,
        castrated = this.castrated,
        image = this.image,
        dateOfBirth = this.dateOfBirth,
        guardianId = this.guardianId?.toIntOrNull() ?: 0,
        petRace = this.breed?.name ?: this.breedAlias,
        petAge = this.dateOfBirth?.let { calculateAge(it) }
    )
}

private fun calculateAge(dateString: String): String {
    return try {
        if (dateString.isBlank()) return ""

        val birthDate = ZonedDateTime.parse(dateString).toLocalDate()
        val currentDate = LocalDate.now()
        val period = Period.between(birthDate, currentDate)

        when {
            period.years > 1 -> "${period.years} anos"
            period.years == 1 -> "1 ano"
            period.months > 1 -> "${period.months} meses"
            period.months == 1 -> "1 mês"
            else -> "Recém nascido"
        }
    } catch (e: Exception) {
        e.message.toString()
    }
}