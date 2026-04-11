package com.soujunior.domain.repository.database

import com.soujunior.domain.model.PetModel
import com.soujunior.domain.model.request.PetRaceItemModel
import com.soujunior.domain.model.request.PetSizeItemModel
import com.soujunior.domain.model.response.GuardianNameResponse
import com.soujunior.domain.use_case.base.DataResult

interface LocalDataSource {
    suspend fun getGuardianName(): String?
    suspend fun getGuardianEmail(): String?
    suspend fun deleteDatabase()
    suspend fun saveGuardianContact(email: String, phone: String)
    suspend fun saveGuardianName(response: GuardianNameResponse)
    suspend fun savePetInformation(petModel: PetModel) : DataResult<Long>
    suspend fun getPetInformation(id: Long) : DataResult<PetModel>
    suspend fun updatePetInformation(petModel: PetModel) : DataResult<Unit>
    suspend fun getListPetSizes(tag: String): DataResult<List<PetSizeItemModel>>?
    suspend fun saveListPetSizes(tag: String, listPetSize: List<PetSizeItemModel>): DataResult<String>
    suspend fun getListPetRaces(tag: String): DataResult<List<PetRaceItemModel>>?
    suspend fun saveListPetRaces(tag: String, listPetRace: List<PetRaceItemModel>): DataResult<String>
}