package com.petjournal.database.repository

import com.petjournal.database.database.dao.GuardianProfileDao
import com.petjournal.database.database.entity.GuardianProfile
import com.soujunior.domain.model.response.GuardianNameResponse
import com.soujunior.domain.repository.GuardianLocalDataSource

class GuardianLocalDataSourceImpl(private val guardianDao: GuardianProfileDao) :
    GuardianLocalDataSource {

    override suspend fun getGuardianName(): String? {
        return guardianDao.getProfile(1)?.firstName
    }

    override suspend fun saveGuardianName(response: GuardianNameResponse) {
        guardianDao.insertProfile(
            GuardianProfile(
                firstName = response.firstName,
                lastName = response.lastName
            )
        )
    }
}
