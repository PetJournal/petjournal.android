package com.soujunior.domain.repository

import com.soujunior.domain.model.PetInformationModel
import com.soujunior.domain.model.request.PetRaceItemModel
import com.soujunior.domain.model.request.PetSizeItemModel
import com.soujunior.domain.model.response.GuardianNameResponse
import com.soujunior.domain.model.response.Pet.PetResponse
import com.soujunior.domain.network.NetworkResult
import com.soujunior.domain.use_case.base.DataResult

interface GuardianRepository {
    suspend fun getGuardianName(): NetworkResult<GuardianNameResponse>
    suspend fun savePet(petInformationModel: PetInformationModel): DataResult<Long>
    suspend fun getPet(idPetInformation: Long): DataResult<PetInformationModel>
    suspend fun updatePet(petInformationModel: PetInformationModel) : DataResult<Unit>
    suspend fun createPet(petInformationModel: PetInformationModel): NetworkResult<PetResponse>
    suspend fun getListPetSizes(petSpecie: String): NetworkResult<List<PetSizeItemModel>>
    suspend fun getListPetRaces(petSpecie: String): NetworkResult<List<PetRaceItemModel>>
}
