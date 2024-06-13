package com.petjournal.database.repository

import com.petjournal.database.converter.Converter.toEntity
import com.petjournal.database.database.dao.ApplicationInformationDao
import com.petjournal.database.database.dao.GuardianProfileDao
import com.petjournal.database.database.entity.ApplicationInformation
import com.petjournal.database.database.entity.GuardianProfile
import com.soujunior.domain.model.PetInformationModel
import com.soujunior.domain.model.response.GuardianNameResponse
import com.soujunior.domain.repository.GuardianLocalDataSource
import com.soujunior.domain.use_case.base.DataResult

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
    override suspend fun getAllPetInformation() : DataResult<List<PetInformationModel>>{
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



    override suspend fun deleteDatabase() {
        guardianDao.deleteAllProfiles()
        appInfoDao.deleteAllInformation()
    }
}