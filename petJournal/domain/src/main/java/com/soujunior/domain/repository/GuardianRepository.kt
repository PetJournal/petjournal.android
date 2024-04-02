package com.soujunior.domain.repository

import com.soujunior.domain.model.mapper.PetInformationModel
import com.soujunior.domain.model.response.GuardianNameResponse
import com.soujunior.domain.network.NetworkResult

interface GuardianRepository {
    suspend fun getGuardianName(): NetworkResult<GuardianNameResponse>
    suspend fun savePetInformation(petInformationModel: PetInformationModel): Long
    suspend fun getPetInformation(idPetInformation: Long): PetInformationModel
}