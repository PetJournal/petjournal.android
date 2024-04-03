package com.soujunior.domain.repository

import com.soujunior.domain.model.mapper.PetInformationModel
import com.soujunior.domain.model.response.GuardianNameResponse
import com.soujunior.domain.use_case.base.DataResult

interface GuardianLocalDataSource {
    suspend fun getGuardianName(): String?
    suspend fun deleteDatabase()
    suspend fun saveGuardianName(response: GuardianNameResponse)
    suspend fun savePetInformation(petInformationModel: PetInformationModel) : DataResult<Long>
    suspend fun getPetInformation(id: Long) : PetInformationModel
}