package com.soujunior.domain.repository

import com.soujunior.domain.model.PetModel
import com.soujunior.domain.model.PetResponseNew
import com.soujunior.domain.model.request.PetRaceItemModel
import com.soujunior.domain.model.request.PetSizeItemModel
import com.soujunior.domain.model.response.GuardianNameResponse
import com.soujunior.domain.model.response.Pet.PetResponse
import com.soujunior.domain.network.NetworkResult
import com.soujunior.domain.use_case.base.DataResult

interface GuardianRepository {
    suspend fun getGuardianName(): NetworkResult<GuardianNameResponse>
    suspend fun savePet(petModel: PetModel): DataResult<Long>

    suspend fun getListPet(): NetworkResult<List<PetResponseNew>>

    suspend fun getPet(idPet: Long): DataResult<PetModel>
    suspend fun updatePet(petModel: PetModel) : DataResult<Unit>
    suspend fun createPet(petModel: PetModel): NetworkResult<PetResponse>

    suspend fun getListPetSizes(petSpecie: String): NetworkResult<List<PetSizeItemModel>>
    suspend fun getListPetRaces(petSpecie: String): NetworkResult<List<PetRaceItemModel>>
}
