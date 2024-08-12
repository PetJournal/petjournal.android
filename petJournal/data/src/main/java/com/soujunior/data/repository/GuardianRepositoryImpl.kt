package com.soujunior.data.repository

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import com.petjournal.database.converter.Converter.toResponse
import com.soujunior.data.remote.GuardianService
import com.soujunior.data.util.manager.JwtManager
import com.soujunior.data.util.toPetInformationItemList
import com.soujunior.domain.model.PetInformationModel
import com.soujunior.domain.model.request.PetRaceItemModel
import com.soujunior.domain.model.request.PetSizeItemModel
import com.soujunior.domain.model.response.GuardianNameResponse
import com.soujunior.domain.model.response.pet_information.PetInformationItem
import com.soujunior.domain.network.NetworkResult
import com.soujunior.domain.network.onError
import com.soujunior.domain.network.onException
import com.soujunior.domain.network.onSuccess
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

    override suspend fun savePetInformation(petInformationModel: PetInformationModel): DataResult<Long> {
        val guardianId = 1
        val petInformation = petInformationModel.copy(
            species = petInformationModel.species,
            guardianId = guardianId
        )
        return try {
            DataResult.Success(guardianLocalDataSourceImpl.savePetInformation(petInformation).success.data)
        } catch (e: Throwable) {
            DataResult.Failure(e)
        }
    }

    override suspend fun getPetInformation(idPetInformation: Long): DataResult<PetInformationModel> {
        return try {
            DataResult.Success(guardianLocalDataSourceImpl.getPetInformation(idPetInformation).success.data)
        } catch (e: Throwable) {
            DataResult.Failure(e)
        }
    }

    override suspend fun updatePetInformation(petInformationModel: PetInformationModel): DataResult<Unit> {
        return try {
            DataResult.Success(guardianLocalDataSourceImpl.updatePetInformation(petInformationModel).success.data)
        } catch (e: Throwable) {
            DataResult.Failure(e)
        }
    }

    override suspend fun deletePetInformation(idPetInformation: Long): DataResult<Unit> {
        return try {
            DataResult.Success(guardianLocalDataSourceImpl.deletePetInformation(idPetInformation).success.data)
        } catch (e: Throwable) {
            DataResult.Failure(e)
        }
    }

    override suspend fun getListPetSizes(petSpecie: String): NetworkResult<List<PetSizeItemModel>> {
        val localListPetSizes =
            guardianLocalDataSourceImpl.getListPetSizes(petSpecie)?.success?.data
        return if (!localListPetSizes.isNullOrEmpty()) {
            NetworkResult.Success(localListPetSizes)
        } else {
            val token = "Bearer " + jwtManager.getToken()
            when (val apiResult = guardianApi.getListPetSizes(token, petSpecie)) {
                is NetworkResult.Success -> {
                    coroutineScope {
                        try {
                            guardianLocalDataSourceImpl.saveListPetSizes(petSpecie, apiResult.data)
                        } catch (e: Exception) {
                            Log.e(TAG, "Exeption: " + e.message)
                        }
                    }

                    NetworkResult.Success(apiResult.data)
                }

                else -> apiResult
            }
        }
    }

    override suspend fun getListPetRaces(petSpecie: String): NetworkResult<List<PetRaceItemModel>> {
        val localListPetRaces =
            guardianLocalDataSourceImpl.getListPetRaces(petSpecie)?.success?.data
        return if (!localListPetRaces.isNullOrEmpty()) {
            NetworkResult.Success(localListPetRaces)
        } else {
            val token = "Bearer " + jwtManager.getToken()
            when (val apiResult = guardianApi.getListPetRaces(token, petSpecie)) {
                is NetworkResult.Success -> {
                    coroutineScope {
                        try {
                            guardianLocalDataSourceImpl.saveListPetRaces(petSpecie, apiResult.data)
                        } catch (e: Exception) {
                            Log.e(TAG, "Exeption: " + e.message)
                        }
                    }

                    NetworkResult.Success(apiResult.data)
                }

                else -> apiResult
            }
        }
    }

    override suspend fun getAllPetInformation(): NetworkResult<List<PetInformationItem>> {
        val localListPetInformation =
            guardianLocalDataSourceImpl.getAllPetInformation().success.data

        return if (!localListPetInformation.isNullOrEmpty()) {
            NetworkResult.Success(localListPetInformation.toPetInformationItemList())
        } else {
            val token = "Bearer " + jwtManager.getToken()

            val apiResult = guardianApi.getAllPetInformation(token)

            apiResult.onSuccess {
                Log.i("API", "$it")
            }

            apiResult.onError { code, body ->
                Log.i("API", "$body")
            }

            apiResult.onException {
                Log.i("API", "$it")
            }

            when (val apiResult = guardianApi.getAllPetInformation(token)) {
                is NetworkResult.Success -> {
                    NetworkResult.Success(apiResult.data)
                }

                else -> apiResult
            }

        }

    }

    override suspend fun createPetInformationApi(petInformationModel: PetInformationModel): NetworkResult<Unit> {
        val token = "Bearer " + jwtManager.getToken()
        val pet = petInformationModel.toResponse()
        return guardianApi.savePetInformation(token, pet)
    }

}
