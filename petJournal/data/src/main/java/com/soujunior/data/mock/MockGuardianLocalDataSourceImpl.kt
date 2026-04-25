package com.soujunior.data.mock

import com.soujunior.domain.model.PetModel
import com.soujunior.domain.model.request.PetRaceItemModel
import com.soujunior.domain.model.request.PetSizeItemModel
import com.soujunior.domain.model.response.GuardianNameResponse
import com.soujunior.domain.model.PetDetailsDTO
import com.soujunior.domain.model.response.tag.TagDTO
import com.soujunior.domain.model.taskModel.ScheduleDataDTO
import com.soujunior.domain.repository.database.LocalDataSource
import com.soujunior.domain.use_case.base.DataResult

class MockGuardianLocalDataSourceImpl:LocalDataSource {
    override suspend fun getGuardianName(): String? {
        TODO("Not yet implemented")
    }

    override suspend fun getGuardianEmail(): String? {
        TODO("Not yet implemented")
    }

    override suspend fun deleteDatabase() {
        TODO("Not yet implemented")
    }

    override suspend fun saveGuardianContact(email: String, phone: String) {
        TODO("Not yet implemented")
    }

    override suspend fun saveGuardianName(response: GuardianNameResponse) {
        TODO("Not yet implemented")
    }

    override suspend fun savePetInformation(petModel: PetModel): DataResult<Long> {
        TODO("Not yet implemented")
    }

    override suspend fun getPetInformation(id: Long): DataResult<PetModel> {
        TODO("Not yet implemented")
    }

    override suspend fun updatePetInformation(petModel: PetModel): DataResult<Unit> {
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

    override suspend fun getAllPets(): List<PetDetailsDTO> {
        TODO("Not yet implemented")
    }

    override suspend fun saveAllPets(pets: List<PetDetailsDTO>) {
        TODO("Not yet implemented")
    }

    override suspend fun getAllTags(): List<TagDTO> {
        TODO("Not yet implemented")
    }

    override suspend fun saveAllTags(tags: List<TagDTO>) {
        TODO("Not yet implemented")
    }

    override suspend fun getAllTasks(): List<ScheduleDataDTO> {
        TODO("Not yet implemented")
    }

    override suspend fun getTasksInPeriod(
        startDate: String,
        endDate: String
    ): List<ScheduleDataDTO> {
        TODO("Not yet implemented")
    }

    override suspend fun saveAllTasks(tasks: List<ScheduleDataDTO>) {
        TODO("Not yet implemented")
    }
}