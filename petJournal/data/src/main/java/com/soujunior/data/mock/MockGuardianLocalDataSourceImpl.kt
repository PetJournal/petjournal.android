package com.soujunior.data.mock

import com.soujunior.domain.model.PetInformationModel
import com.soujunior.domain.model.request.PetRaceItemModel
import com.soujunior.domain.model.request.PetSizeItemModel
import com.soujunior.domain.model.response.GuardianNameResponse
import com.soujunior.domain.repository.GuardianLocalDataSource
import com.soujunior.domain.use_case.base.DataResult

class MockGuardianLocalDataSourceImpl:GuardianLocalDataSource {
    override suspend fun getGuardianName(): String? {
        TODO("Not yet implemented")
    }

    override suspend fun deleteDatabase() {
        TODO("Not yet implemented")
    }

    override suspend fun saveGuardianName(response: GuardianNameResponse) {
        TODO("Not yet implemented")
    }

    override suspend fun savePetInformation(petInformationModel: PetInformationModel): DataResult<Long> {
        TODO("Not yet implemented")
    }

    override suspend fun getPetInformation(id: Long): DataResult<PetInformationModel> {
        TODO("Not yet implemented")
    }

    override suspend fun updatePetInformation(petInformationModel: PetInformationModel): DataResult<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun getListPetSizes(tag: String): DataResult<List<PetSizeItemModel>>? {
        TODO("Not yet implemented")
    }

    override suspend fun saveListPetSizes(
        tag: String,
        listPetSize: List<PetSizeItemModel>
    ): DataResult<String> {
        TODO("Not yet implemented")
    }

    override suspend fun getListPetRaces(tag: String): DataResult<List<PetRaceItemModel>>? {
        TODO("Not yet implemented")
    }

    override suspend fun saveListPetRaces(
        tag: String,
        listPetRace: List<PetRaceItemModel>
    ): DataResult<String> {
        TODO("Not yet implemented")
    }
}