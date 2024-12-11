package com.soujunior.domain.repository

import com.soujunior.domain.model.PetInformationModel
import com.soujunior.domain.model.request.PetRaceItemModel
import com.soujunior.domain.model.request.PetSizeItemModel
import com.soujunior.domain.model.response.GuardianNameResponse
import com.soujunior.domain.use_case.base.DataResult

interface GuardianLocalDataSource {
    suspend fun getGuardianName(): String?
    suspend fun deleteDatabase()
    suspend fun saveGuardianName(response: GuardianNameResponse)
    suspend fun savePetInformation(petInformationModel: PetInformationModel) : DataResult<Long>
    suspend fun getPetInformation(id: Long) : DataResult<PetInformationModel>
    suspend fun updatePetInformation(petInformationModel: PetInformationModel) : DataResult<Unit>
    suspend fun getListPetSizes(tag: String): DataResult<List<PetSizeItemModel>>?
    suspend fun saveListPetSizes(tag: String, listPetSize: List<PetSizeItemModel>): DataResult<String>
    suspend fun getListPetRaces(tag: String): DataResult<List<PetRaceItemModel>>?
    suspend fun saveListPetRaces(tag: String, listPetRace: List<PetRaceItemModel>): DataResult<String>

}