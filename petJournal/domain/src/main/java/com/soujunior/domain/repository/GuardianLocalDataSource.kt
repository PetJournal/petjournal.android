package com.soujunior.domain.repository

import android.provider.ContactsContract.Data
import com.soujunior.domain.model.PetInformationModel
import com.soujunior.domain.model.response.GuardianNameResponse
import com.soujunior.domain.use_case.base.DataResult

interface GuardianLocalDataSource {
    suspend fun getGuardianName(): String?
    suspend fun deleteDatabase()
    suspend fun saveGuardianName(response: GuardianNameResponse)
    suspend fun savePetInformation(petInformationModel: PetInformationModel) : DataResult<Long>
    suspend fun getPetInformation(id: Long) : DataResult<PetInformationModel>
    suspend fun getAllPetInformation() : DataResult<List<PetInformationModel>>
    suspend fun deletePetInformation(id: Long) : DataResult<Unit>
    suspend fun updatePetInformation(petInformationModel: PetInformationModel) : DataResult<Unit>
}