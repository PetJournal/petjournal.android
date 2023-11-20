package com.petjournal.database.repository

import com.petjournal.database.database.dao.ApplicationInformationDao
import com.petjournal.database.database.dao.GuardianProfileDao
import com.petjournal.database.database.entity.ApplicationInformation
import com.petjournal.database.database.entity.GuardianProfile
import com.soujunior.domain.model.response.GuardianNameResponse
import com.soujunior.domain.repository.GuardianLocalDataSource

class GuardianLocalDataSourceImpl(
    private val guardianDao: GuardianProfileDao,
    private val appInfoDao: ApplicationInformationDao,
) :
    GuardianLocalDataSource {

    override suspend fun getGuardianName(): String? {
        return guardianDao.getProfile(1)?.firstName
    }

    override suspend fun saveGuardianName(response: GuardianNameResponse) {
        //É chamado assim que o app é logado
        guardianDao.insertProfile(
            GuardianProfile(
                firstName = response.firstName,
                lastName = response.lastName
            )
        )
        appInfoDao.insertInformation(ApplicationInformation(1, false))
    }

    override suspend fun deleteDatabase() {
        guardianDao.deleteAllProfiles()
    }
}
