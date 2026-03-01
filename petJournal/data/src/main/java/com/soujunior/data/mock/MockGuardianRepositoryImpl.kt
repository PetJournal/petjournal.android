package com.soujunior.data.mock

import com.soujunior.domain.model.BreedDTO
import com.soujunior.domain.model.PetCreateDTO
import com.soujunior.domain.model.PetModel
import com.soujunior.domain.model.PetDetailsDTO
import com.soujunior.domain.model.SizeDTO
import com.soujunior.domain.model.request.PetRaceItemModel
import com.soujunior.domain.model.request.PetSizeItemModel
import com.soujunior.domain.model.response.GuardianNameResponse
import com.soujunior.domain.model.response.tag.TagDTO
import com.soujunior.domain.model.response.tag.UpdatePetByIdDTO
import com.soujunior.domain.network.NetworkResult
import com.soujunior.domain.repository.GuardianRepository
import com.soujunior.domain.use_case.base.DataResult
import java.io.File

class MockGuardianRepositoryImpl(): GuardianRepository {
    override suspend fun getGuardianName(): NetworkResult<GuardianNameResponse> = NetworkResult.Success (GuardianNameResponse("Petjornaleiro", "Jr"))

    override suspend fun savePet(petModel: PetModel): DataResult<Long> {
        TODO("Not yet implemented")
    }

    override suspend fun getListSize(animal: String): NetworkResult<List<SizeDTO>> {
        TODO("Not yet implemented")
    }

    override suspend fun getListBreed(animal: String): NetworkResult<List<BreedDTO>> {
        TODO("Not yet implemented")
    }

    override suspend fun getListTag(): NetworkResult<List<TagDTO>> {
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

    override suspend fun getListPet(): NetworkResult<List<PetDetailsDTO>> {
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

    override suspend fun updatePet(petModel: PetModel): DataResult<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun getListPetSizes(petSpecie: String): NetworkResult<List<PetSizeItemModel>> {
        TODO("Not yet implemented")
    }

    override suspend fun getListPetRaces(petSpecie: String): NetworkResult<List<PetRaceItemModel>> {
        TODO("Not yet implemented")
    }
}