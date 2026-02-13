package com.soujunior.data.mock

import com.soujunior.domain.model.PetModel
import com.soujunior.domain.model.PetResponseNew
import com.soujunior.domain.model.request.PetRaceItemModel
import com.soujunior.domain.model.request.PetSizeItemModel
import com.soujunior.domain.model.response.GuardianNameResponse
import com.soujunior.domain.model.response.Pet.PetResponse
import com.soujunior.domain.network.NetworkResult
import com.soujunior.domain.repository.GuardianRepository
import com.soujunior.domain.use_case.base.DataResult

class MockGuardianRepositoryImpl(): GuardianRepository {
    override suspend fun getGuardianName(): NetworkResult<GuardianNameResponse> = NetworkResult.Success (GuardianNameResponse("Petjornaleiro", "Jr"))

    override suspend fun savePet(petModel: PetModel): DataResult<Long> {
        TODO("Not yet implemented")
    }

    override suspend fun getListPet(): NetworkResult<List<PetResponseNew>> {
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

    override suspend fun createPet(petModel: PetModel): NetworkResult<PetResponse> {
        TODO("Not yet implemented")
    }
}