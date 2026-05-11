package com.petjournal.database.repository

import com.petjournal.database.database.dao.ApplicationInformationDao
import com.soujunior.domain.repository.appinfo.AppInfoDatabase

class AppInfoDataBaseImpl(private val appInfoDao: ApplicationInformationDao): AppInfoDatabase {
    override suspend fun getIsPetRegistrationWentLive(): Boolean {
        return appInfoDao.getPetRegistrationWentLive(1)
    }

    override suspend fun setIsPetRegistrationWentLive(visualized: Boolean) {
        appInfoDao.setPetRegistrationWentLive(1, visualized)
    }
}
