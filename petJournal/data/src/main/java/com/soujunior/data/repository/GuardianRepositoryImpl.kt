package com.soujunior.data.repository

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import com.soujunior.data.remote.GuardianService
import com.soujunior.data.util.manager.JwtManager
import com.soujunior.domain.model.PetInformationModel
import com.soujunior.domain.model.response.GuardianNameResponse
import com.soujunior.domain.network.NetworkResult
import com.soujunior.domain.repository.GuardianLocalDataSource
import com.soujunior.domain.repository.GuardianRepository
import com.soujunior.domain.use_case.base.DataResult
import kotlinx.coroutines.coroutineScope

class GuardianRepositoryImpl(
    private val guardianApi: GuardianService,
    private val guardianLocalDataSourceImpl: GuardianLocalDataSource,
    context: Context
) : GuardianRepository {

    private val jwtManager: JwtManager = JwtManager.getInstance(context)
    override suspend fun getGuardianName(): NetworkResult<GuardianNameResponse> {
        val localName = guardianLocalDataSourceImpl.getGuardianName()
        return if (localName != null) {
            NetworkResult.Success(GuardianNameResponse(localName, ""))
        } else {
            val token = "Bearer " + jwtManager.getToken()
            when (val apiResult = guardianApi.getGuardianName(token)) {
                is NetworkResult.Success -> {
                    coroutineScope {
                        try {
                            guardianLocalDataSourceImpl.saveGuardianName(apiResult.data)
                        } catch (e: Exception) {
                            Log.e(TAG, "Exeption: " + e.message)
                        }
                    }
                    NetworkResult.Success(GuardianNameResponse(apiResult.data.firstName, ""))
                }

                else -> apiResult
            }
        }
    }

    override suspend fun savePetInformation(petInformationModel: PetInformationModel) : DataResult<Long>  {
        val guardianId = 1
        val petInformation = petInformationModel.copy(
            species = petInformationModel.species,
            guardianId = guardianId
        )
        return try {
            DataResult.Success(guardianLocalDataSourceImpl.savePetInformation(petInformation).success.data)
        } catch (e: Throwable){
            DataResult.Failure(e)
        }
    }

    override suspend fun getPetInformation(idPetInformation: Long): DataResult<PetInformationModel> {
        return try {
            DataResult.Success(guardianLocalDataSourceImpl.getPetInformation(idPetInformation).success.data)
        }catch (e: Throwable){
            DataResult.Failure(e)
        }
    }

    override suspend fun updatePetInformation(petInformationModel: PetInformationModel): DataResult<Unit> {
        return try {
            DataResult.Success(guardianLocalDataSourceImpl.updatePetInformation(petInformationModel).success.data)
        }catch (e: Throwable){
            DataResult.Failure(e)
        }
    }

    override suspend fun deletePetInformation(idPetInformation: Long): DataResult<Unit> {
        return try{
            DataResult.Success(guardianLocalDataSourceImpl.deletePetInformation(idPetInformation).success.data)
        }catch (e: Throwable){
            DataResult.Failure(e)
        }
    }

    override suspend fun getAllPetInformation(): DataResult<List<PetInformationModel>> {
        return try {
            DataResult.Success(guardianLocalDataSourceImpl.getAllPetInformation().success.data)
        }catch (e: Throwable){
            DataResult.Failure(e)
        }
    }
}
