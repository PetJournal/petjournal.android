package com.petjournal.database.repository

import com.petjournal.database.converter.Converter.toEntity
import com.petjournal.database.converter.Converter.toListPetRaceEntity
import com.petjournal.database.converter.Converter.toListPetRaceModel
import com.petjournal.database.converter.Converter.toListPetSizeEntity
import com.petjournal.database.converter.Converter.toListPetSizeItemModel
import com.petjournal.database.database.dao.ApplicationInformationDao
import com.petjournal.database.database.dao.GuardianProfileDao
import com.petjournal.database.database.entity.ApplicationInformation
import com.petjournal.database.database.entity.GuardianProfile
import com.soujunior.domain.model.PetInformationModel
import com.soujunior.domain.model.request.PetRaceItemModel
import com.soujunior.domain.model.request.PetSizeItemModel
import com.soujunior.domain.model.response.GuardianNameResponse
import com.soujunior.domain.repository.GuardianLocalDataSource
import com.soujunior.domain.use_case.base.DataResult
import kotlinx.coroutines.flow.Flow

class GuardianLocalDataSourceImpl(
    private val guardianDao: GuardianProfileDao,
    private val appInfoDao: ApplicationInformationDao,
) : GuardianLocalDataSource {

    override suspend fun getGuardianName(): String? {
        return guardianDao.getProfile(1)?.firstName
    }

    override suspend fun saveGuardianName(response: GuardianNameResponse) {
        if (getGuardianName() == null) {
            guardianDao.insertProfile(
                GuardianProfile(
                    id = 1,
                    firstName = response.firstName,
                    lastName = response.lastName
                )
            )
            appInfoDao.insertInformation(ApplicationInformation(1, false))
        } else {
            deleteDatabase()
            saveGuardianName(response)
        }

    }

    override suspend fun savePetInformation(petInformationModel: PetInformationModel): DataResult<Long> {
        return try {
            DataResult.Success(
                guardianDao.insertPetInformation(petInformationModel.toEntity())
            )
        } catch (e: Throwable) {
            DataResult.Failure(e)
        }

    }
    override suspend fun getAllPetInformation() : DataResult<Flow<List<PetInformationModel>>>{
        return try{
            DataResult.Success(guardianDao.getAllPetInformation())
        }
        catch (e:Throwable){
            DataResult.Failure(e)
        }
    }

    override suspend fun deletePetInformation(id: Long): DataResult<Unit> {
        return try {
            DataResult.Success(guardianDao.deletePetInformation(id))
        } catch (e: Throwable) {
            DataResult.Failure(e)
        }
    }

    override suspend fun getPetInformation(id: Long): DataResult<PetInformationModel> {
        return try {
            DataResult.Success(guardianDao.getPetInformation(id))
        } catch (e: Throwable) {
            DataResult.Failure(e)
        }
    }

    override suspend fun updatePetInformation(petInformationModel: PetInformationModel): DataResult<Unit> {
        return try {
            DataResult.Success(guardianDao.updatePetInformation(petInformationModel.toEntity()))
        } catch (e: Throwable) {
            DataResult.Failure(e)
        }
    }

    override suspend fun getListPetSizes(tag: String): DataResult<List<PetSizeItemModel>>? {
        return try {
            val data = guardianDao.getListPetSizes(tag)
            if (data != null) {
                DataResult.Success(data.toListPetSizeItemModel())
            } else {
                DataResult.Success(emptyList())
            }
        } catch (error: Throwable) {
            DataResult.Failure(error)
        }
    }


    override suspend fun saveListPetSizes(
        tag: String,
        listPetSize: List<PetSizeItemModel>
    ): DataResult<String> {
        return try {
            guardianDao.insertListPetSizes(listPetSize.toListPetSizeEntity(tag))
            DataResult.Success(tag)
        } catch (e: Throwable) {
            DataResult.Failure(e)
        }

    }

    override suspend fun getListPetRaces(tag: String): DataResult<List<PetRaceItemModel>>? {
        return try {
            val data = guardianDao.getListPetRaces(tag)
            if (data != null) {
                DataResult.Success(data.toListPetRaceModel())
            } else {
                DataResult.Success(emptyList())
            }
        } catch (error: Throwable) {
            DataResult.Failure(error)
        }
    }

    override suspend fun saveListPetRaces(
       tag: String, listPetRace: List<PetRaceItemModel>
    ): DataResult<String> {
        return try {
            guardianDao.insertListPetRaces(listPetRace.toListPetRaceEntity(tag))
            DataResult.Success(tag)
        } catch (e: Throwable) {
            DataResult.Failure(e)
        }
    }



    override suspend fun deleteDatabase() {
        guardianDao.deleteAllProfiles()
        appInfoDao.deleteAllInformation()
    }
}