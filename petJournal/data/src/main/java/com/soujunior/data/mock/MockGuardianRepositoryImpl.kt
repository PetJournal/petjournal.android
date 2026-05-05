package com.soujunior.data.mock

import com.soujunior.domain.model.BreedDTO
import com.soujunior.domain.model.PetCreateDTO
import com.soujunior.domain.model.PetModel
import com.soujunior.domain.model.PetDetailsDTO
import com.soujunior.domain.model.SizeDTO
import com.soujunior.domain.model.request.PetRaceItemModel
import com.soujunior.domain.model.request.PetSizeItemModel
import com.soujunior.domain.model.request.taskModels.TaskDTO
import com.soujunior.domain.model.response.GuardianNameResponse
import com.soujunior.domain.model.response.tag.TagDTO
import com.soujunior.domain.model.response.tag.UpdatePetByIdDTO
import com.soujunior.domain.model.taskModel.PaginatedScheduleResponseDTO
import com.soujunior.domain.network.NetworkResult
import com.soujunior.domain.repository.api.Repository
import com.soujunior.domain.use_case.base.DataResult

class MockGuardianRepositoryImpl(): Repository {
    override suspend fun getGuardianName(forceRequest: Boolean): NetworkResult<GuardianNameResponse> = NetworkResult.Success (GuardianNameResponse("Petjornaleiro", "Jr"))

    override suspend fun getGuardianEmail(): String? {
        return "mock@email.com"
    }

    override suspend fun saveGuardianContact(email: String, phone: String) {
    }
    override suspend fun savePet(petModel: PetModel): DataResult<Long> {
        TODO("Not yet implemented")
    }

    override suspend fun getListSize(animal: String): NetworkResult<List<SizeDTO>> {
        TODO("Not yet implemented")
    }

    override suspend fun getListBreed(animal: String): NetworkResult<List<BreedDTO>> {
        TODO("Not yet implemented")
    }

    override suspend fun getListTag(forceRequest: Boolean): NetworkResult<List<TagDTO>> {
        TODO("Not yet implemented")
    }

    override suspend fun createTag(tag: TagDTO): NetworkResult<TagDTO> {
        TODO("Not yet implemented")
    }

    override suspend fun updateTag(tag: TagDTO): NetworkResult<UpdatePetByIdDTO> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTag(id: String): NetworkResult<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun getListPet(forceRequest: Boolean): NetworkResult<List<PetDetailsDTO>> {
        TODO("Not yet implemented")
    }

    override suspend fun createPet(
        pet: PetCreateDTO,
        imageUri: String?
    ): NetworkResult<PetDetailsDTO> {
        TODO("Not yet implemented")
    }

    override suspend fun getPet(idPet: Long): DataResult<PetModel> {
        TODO("Not yet implemented")
    }

    override suspend fun getPetById(id: String): NetworkResult<PetDetailsDTO> {
        TODO("Not yet implemented")
    }

    override suspend fun deletePetById(id: String): NetworkResult<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun updatePet(
        id: String,
        pet: PetCreateDTO,
        imageUri: String?
    ): NetworkResult<PetDetailsDTO> {
        TODO("Not yet implemented")
    }

    override suspend fun getListPetSizes(petSpecie: String): NetworkResult<List<PetSizeItemModel>> {
        TODO("Not yet implemented")
    }

    override suspend fun getListPetRaces(petSpecie: String): NetworkResult<List<PetRaceItemModel>> {
        TODO("Not yet implemented")
    }

    override suspend fun saveTaskLocal(task: TaskDTO): DataResult<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun scheduled(item: TaskDTO): NetworkResult<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun listTasksByPeriod(
        startDate: String,
        endDate: String,
        forceRequest: Boolean
    ): NetworkResult<PaginatedScheduleResponseDTO> {
        TODO("Not yet implemented")
    }

    override suspend fun getLocalTasksByPeriod(
        startDate: String,
        endDate: String,
        considerTime: Boolean
    ): DataResult<PaginatedScheduleResponseDTO> {
        TODO("Not yet implemented")
    }

    override suspend fun listCurrentDateScheduled(forceRequest: Boolean): NetworkResult<PaginatedScheduleResponseDTO> {
        TODO("Not yet implemented")
    }

    override suspend fun listCurrentWeekScheduled(forceRequest: Boolean): NetworkResult<PaginatedScheduleResponseDTO> {
        TODO("Not yet implemented")
    }

    override suspend fun listCurrentMonthScheduled(forceRequest: Boolean): NetworkResult<PaginatedScheduleResponseDTO> {
        TODO("Not yet implemented")
    }
}