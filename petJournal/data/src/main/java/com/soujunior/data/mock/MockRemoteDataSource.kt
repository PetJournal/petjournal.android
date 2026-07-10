package com.soujunior.data.mock

import com.soujunior.data.remote.RemoteDataSource
import com.soujunior.domain.model.BreedDTO
import com.soujunior.domain.model.PetDetailsDTO
import com.soujunior.domain.model.SizeDTO
import com.soujunior.domain.model.request.PetRaceItemModel
import com.soujunior.domain.model.request.PetSizeItemModel
import com.soujunior.domain.model.request.taskModels.TaskDTO
import com.soujunior.domain.model.response.GuardianNameResponse
import com.soujunior.domain.model.response.UserInfoResponse
import com.soujunior.domain.model.response.tag.TagDTO
import com.soujunior.domain.model.response.tag.UpdatePetByIdDTO
import com.soujunior.domain.model.taskModel.PaginatedNextEventsResponseDTO
import com.soujunior.domain.model.taskModel.PaginatedScheduleResponseDTO
import com.soujunior.domain.network.NetworkResult
import kotlinx.coroutines.delay
import okhttp3.MultipartBody
import okhttp3.RequestBody

class MockRemoteDataSource : RemoteDataSource {
    
    override suspend fun getGuardianName(token: String): NetworkResult<GuardianNameResponse> {
        delay(1000)
        return NetworkResult.Success(MockDataProvider.getMockGuardianNameResponse())
    }

    override suspend fun getGuardianProfile(token: String): NetworkResult<UserInfoResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun getListPetSizes(
        token: String,
        petSpecie: String
    ): NetworkResult<List<PetSizeItemModel>> {
        delay(1000)
        return NetworkResult.Success(MockDataProvider.getMockPetSizes(petSpecie))
    }

    override suspend fun getListPetRaces(
        token: String,
        petSpecie: String
    ): NetworkResult<List<PetRaceItemModel>> {
        delay(1000)
        return NetworkResult.Success(MockDataProvider.getMockPetRaces(petSpecie))
    }

    override suspend fun createPet(
        token: String,
        image: MultipartBody.Part?,
        specieName: RequestBody,
        petName: RequestBody,
        gender: RequestBody,
        breedName: RequestBody,
        size: RequestBody,
        castrated: RequestBody,
        dateOfBirth: RequestBody
    ): NetworkResult<PetDetailsDTO> {
        TODO("Not yet implemented")
    }

    override suspend fun getPetList(token: String): NetworkResult<List<PetDetailsDTO>> {
        TODO("Not yet implemented")
    }

    override suspend fun getPetById(
        token: String,
        id: String
    ): NetworkResult<PetDetailsDTO> {
        TODO("Not yet implemented")
    }

    override suspend fun deletePetById(
        token: String,
        id: String
    ): NetworkResult<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun updatePet(
        token: String,
        id: String,
        image: MultipartBody.Part?,
        specieName: RequestBody,
        petName: RequestBody,
        gender: RequestBody,
        breedName: RequestBody,
        size: RequestBody,
        castrated: RequestBody,
        dateOfBirth: RequestBody
    ): NetworkResult<PetDetailsDTO> {
        TODO("Not yet implemented")
    }

    override suspend fun getListBreeds(
        token: String,
        animal: String
    ): NetworkResult<List<BreedDTO>> {
        TODO("Not yet implemented")
    }

    override suspend fun getListSize(
        token: String,
        animal: String
    ): NetworkResult<List<SizeDTO>> {
        TODO("Not yet implemented")
    }

    override suspend fun getListTag(token: String): NetworkResult<List<TagDTO>> {
        TODO("Not yet implemented")
    }

    override suspend fun createTag(
        token: String,
        tagDTO: TagDTO
    ): NetworkResult<TagDTO> {
        TODO("Not yet implemented")
    }

    override suspend fun updateTag(
        token: String,
        id: String,
        tagDTO: TagDTO
    ): NetworkResult<UpdatePetByIdDTO> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTag(
        token: String,
        id: String
    ): NetworkResult<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun scheduled(
        token: String,
        item: TaskDTO
    ): NetworkResult<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun getTaskListCurrentDate(token: String): NetworkResult<PaginatedScheduleResponseDTO> {
        TODO("Not yet implemented")
    }

    override suspend fun getTaskListCurrentWeek(token: String): NetworkResult<PaginatedScheduleResponseDTO> {
        TODO("Not yet implemented")
    }

    override suspend fun getTaskListCurrentMonth(token: String): NetworkResult<PaginatedScheduleResponseDTO> {
        TODO("Not yet implemented")
    }

    override suspend fun getNextEventsForPet(
        token: String,
        petId: String
    ): NetworkResult<PaginatedNextEventsResponseDTO> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteOnlyThisTaskById(
        token: String,
        id: String
    ): NetworkResult<Unit> {
        return NetworkResult.Success(Unit)
    }

    override suspend fun deleteAllTasksById(
        token: String,
        id: String
    ): NetworkResult<Unit> {
        TODO("Not yet implemented")
    }
}


