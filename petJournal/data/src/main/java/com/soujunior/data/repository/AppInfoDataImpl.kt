package com.soujunior.data.repository

import com.petjournal.database.repository.AppInfoDataBaseImpl
import com.soujunior.domain.repository.appinfo.AppInfoDatabaseRepository

class AppInfoDataImpl(private val db: AppInfoDataBaseImpl ) : AppInfoDatabaseRepository {
    override suspend fun getIsPetRegistrationWentLive(): Boolean {
        return db.getIsPetRegistrationWentLive()
    }

    override suspend fun setIsPetRegistrationWentLive(visualized: Boolean) {
        db.setIsPetRegistrationWentLive(visualized)
    }
}
