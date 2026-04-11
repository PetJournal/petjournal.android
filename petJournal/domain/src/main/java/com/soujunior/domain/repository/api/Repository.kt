package com.soujunior.domain.repository.api

import com.soujunior.domain.model.BreedDTO
import com.soujunior.domain.model.PetCreateDTO
import com.soujunior.domain.model.PetModel
import com.soujunior.domain.model.PetDetailsDTO
import com.soujunior.domain.model.SizeDTO
import com.soujunior.domain.model.response.tag.TagDTO
import com.soujunior.domain.model.request.PetRaceItemModel
import com.soujunior.domain.model.request.PetSizeItemModel
import com.soujunior.domain.model.request.taskModels.TaskDTO
import com.soujunior.domain.model.response.GuardianNameResponse
import com.soujunior.domain.model.response.tag.UpdatePetByIdDTO
import com.soujunior.domain.model.taskModel.PaginatedScheduleResponseDTO
import com.soujunior.domain.network.NetworkResult
import com.soujunior.domain.use_case.base.DataResult

interface Repository {
    suspend fun getGuardianName(): NetworkResult<GuardianNameResponse>
    suspend fun getGuardianEmail(): String?
    suspend fun saveGuardianContact(email: String, phone: String)

    suspend fun getListSize(animal: String): NetworkResult<List<SizeDTO>>
    suspend fun getListBreed(animal: String): NetworkResult<List<BreedDTO>>

    suspend fun getListTag(): NetworkResult<List<TagDTO>>
    suspend fun createTag(tag: TagDTO): NetworkResult<TagDTO>
    suspend fun updateTag(tag: TagDTO): NetworkResult<UpdatePetByIdDTO>
    suspend fun deleteTag(id: String): NetworkResult<Unit>

    suspend fun savePet(petModel: PetModel): DataResult<Long>
    suspend fun getListPet(): NetworkResult<List<PetDetailsDTO>>
    suspend fun createPet(pet: PetCreateDTO, imageUri: String?): NetworkResult<PetDetailsDTO>
    suspend fun getPet(idPet: Long): DataResult<PetModel>
    suspend fun getPetById(id: String) : NetworkResult<PetDetailsDTO>
    suspend fun deletePetById(id: String) : NetworkResult<Unit>
    suspend fun updatePet(id: String, pet: PetCreateDTO, imageUri: String?) : NetworkResult<PetDetailsDTO>
    suspend fun getListPetSizes(petSpecie: String): NetworkResult<List<PetSizeItemModel>>
    suspend fun getListPetRaces(petSpecie: String): NetworkResult<List<PetRaceItemModel>>

    suspend fun scheduled(item: TaskDTO): NetworkResult<Unit>
    suspend fun listCurrentDateScheduled(): NetworkResult<PaginatedScheduleResponseDTO>
    suspend fun listCurrentWeekScheduled(): NetworkResult<PaginatedScheduleResponseDTO>
    suspend fun listCurrentMonthScheduled(): NetworkResult<PaginatedScheduleResponseDTO>
}
