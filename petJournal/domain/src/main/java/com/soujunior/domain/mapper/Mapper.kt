package com.soujunior.domain.mapper

import com.soujunior.domain.model.BreedDTO
import com.soujunior.domain.model.PetCreateDTO
import com.soujunior.domain.model.PetDetailsDTO
import com.soujunior.domain.model.PetFormRequest
import com.soujunior.domain.model.PetModel
import com.soujunior.domain.model.PetModelV2
import com.soujunior.domain.model.SizeDTO
import com.soujunior.domain.model.response.pet.BreedModel
import com.soujunior.domain.model.response.pet.SizeModel
import com.soujunior.domain.model.response.tag.TagDTO
import com.soujunior.domain.model.response.tag.TagModel
import com.soujunior.domain.model.taskModel.PaginatedScheduleResponseDTO
import com.soujunior.domain.model.taskModel.PaginatedScheduleResponseModel
import com.soujunior.domain.model.taskModel.ScheduleDataDTO
import com.soujunior.domain.model.taskModel.ScheduleDataModel
import com.soujunior.domain.model.taskModel.SchedulerDTO
import com.soujunior.domain.model.taskModel.SchedulerModel
import java.time.LocalDate
import java.time.Period
import java.time.ZonedDateTime

object Mapper {

    fun TagModel.toDTO(): TagDTO {
        return TagDTO(
            id = this.id,
            guardianId = this.guardianId,
            name = this.name,
            color = this.color,
        )
    }

    fun TagDTO.toDomain(): TagModel {
        return TagModel(
            id = this.id,
            guardianId = this.guardianId,
            name = this.name,
            color = this.color,
        )
    }

    @JvmName("tagListToDomain")
    fun List<TagDTO>.toDomain(): List<TagModel> {
        return this.map { it.toDomain() }
    }

    @JvmName("sizeListToDomain")
    fun List<SizeDTO>.toDomain(): List<SizeModel>{
        return this.map { it.toDomain() }
    }

    fun SizeDTO.toDomain(): SizeModel{
        return SizeModel(
            id = this.id,
            name = this.name
        )
    }

    fun BreedDTO.toDomain(): BreedModel{
        return BreedModel(
            id = this.id,
            specieId = this.specieId,
            name = this.name
        )
    }

    @JvmName("breedListToDomain")
    fun List<BreedDTO>.toDomain(): List<BreedModel>{
        return this.map { it.toDomain() }
    }

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

    private fun formatDateToBackend(dateOfBirth: String?): String {
        if (dateOfBirth.isNullOrBlank() || dateOfBirth.length < 8) return ""

        return try {
            val day = dateOfBirth.substring(0, 2)
            val month = dateOfBirth.substring(2, 4)
            val year = dateOfBirth.substring(4, 8)

            "${year}-${month}-${day}T00:00:00Z"
        }
        catch (e: Exception) {
            ""
        }
    }

    fun List<PetDetailsDTO>.toPetModelList(): List<PetModel> {
        return this.map { it.toPetModel() }
    }

    fun PetDetailsDTO.toPetModel(): PetModel {
        return PetModel(
            id = this.id?.toLongOrNull() ?: this.id?.hashCode()?.toLong() ?: 0L,
            idPet = this.id,
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

    fun List<PetDetailsDTO>.toPetModelListV2(): List<PetModelV2> {
        return this.map { it.toPetModelV2() }
    }

    fun PetDetailsDTO.toPetModelV2(): PetModelV2 {
        return PetModelV2(
            id = this.id,
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
    @JvmName("SchedulerDTOtoDomain")
    fun SchedulerDTO.toDomain(): SchedulerModel {
        return SchedulerModel(
            id = id,
            tagId = tagId,
            guardianId = guardianId,
            title = title,
            description = description,
            note = note,
            startAt = startAt,
            endAt = endAt,
            daysOfWeek = daysOfWeek,
            daysOfMonth = daysOfMonth,
            daily = daily,
            tag = tag.toDomain(),
            pets = pets.toPetModelListV2()
        )
    }

    @JvmName("ScheduleDataDTOtoDomain")
    fun ScheduleDataDTO.toDomain(): ScheduleDataModel {
        return ScheduleDataModel(
            id = id,
            schedulerId = schedulerId,
            start = start,
            end = end,
            scheduler = scheduler.toDomain()
        )
    }

    fun List<ScheduleDataDTO>.toListDomain(): List<ScheduleDataModel> = map { it.toDomain() }


    @JvmName("PaginatedScheduleResponseDTOtoDomain")
    fun PaginatedScheduleResponseDTO.toDomain(): PaginatedScheduleResponseModel{
        return PaginatedScheduleResponseModel(
            data = data.toListDomain(),
            page = page,
            limit = limit,
            count = count
        )
    }

}
