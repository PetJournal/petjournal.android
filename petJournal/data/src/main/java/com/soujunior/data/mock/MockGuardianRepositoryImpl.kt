package com.soujunior.data.mock

import android.content.Context
import com.soujunior.data.remote.GuardianService
import com.soujunior.domain.model.PetInformationModel
import com.soujunior.domain.model.request.PetRaceItemModel
import com.soujunior.domain.model.request.PetSizeItemModel
import com.soujunior.domain.model.response.GuardianNameResponse
import com.soujunior.domain.network.NetworkResult
import com.soujunior.domain.repository.GuardianLocalDataSource
import com.soujunior.domain.repository.GuardianRepository
import com.soujunior.domain.use_case.base.DataResult

class MockGuardianRepositoryImpl(
    //private val guardianApi: MockGuardianService,
    //private val guardianLocalDataSourceImpl: GuardianLocalDataSource,
    //context: Context
): GuardianRepository {
    override suspend fun getGuardianName(): NetworkResult<GuardianNameResponse> = NetworkResult.Success (GuardianNameResponse("Petjornaleiro", "Jr"))

    override suspend fun savePet(petInformationModel: PetInformationModel): DataResult<Long> {
        TODO("Not yet implemented")
    }

    override suspend fun getPet(idPetInformation: Long): DataResult<PetInformationModel> {
        TODO("Not yet implemented")
    }

    override suspend fun updatePet(petInformationModel: PetInformationModel): DataResult<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun getListPetSizes(petSpecie: String): NetworkResult<List<PetSizeItemModel>> {
        TODO("Not yet implemented")
    }

    override suspend fun getListPetRaces(petSpecie: String): NetworkResult<List<PetRaceItemModel>> {
        TODO("Not yet implemented")
    }

    override suspend fun createPet(petInformationModel: PetInformationModel): NetworkResult<Unit> {
        TODO("Not yet implemented")
    }
}