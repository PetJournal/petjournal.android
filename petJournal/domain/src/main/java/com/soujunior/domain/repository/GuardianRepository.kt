package com.soujunior.domain.repository

import com.soujunior.domain.model.PetInformationModel
import com.soujunior.domain.model.response.GuardianNameResponse
import com.soujunior.domain.network.NetworkResult
import com.soujunior.domain.use_case.base.DataResult

interface GuardianRepository {
    suspend fun getGuardianName(): NetworkResult<GuardianNameResponse>
    suspend fun savePetInformation(petInformationModel: PetInformationModel): DataResult<Long>
    suspend fun getPetInformation(idPetInformation: Long): PetInformationModel
}