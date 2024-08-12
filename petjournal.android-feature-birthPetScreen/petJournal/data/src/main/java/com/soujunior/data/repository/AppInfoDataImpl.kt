package com.soujunior.data.repository

import com.petjournal.database.repository.AppInfoDataBaseImpl
import com.soujunior.domain.repository.AppInfoDataBaseRepository

class AppInfoDataImpl(private val db: AppInfoDataBaseImpl ) : AppInfoDataBaseRepository {
    override suspend fun getIsPetRegistrationWentLive(): Boolean {
        return db.getIsPetRegistrationWentLive()
    }

    override suspend fun setIsPetRegistrationWentLive(visualized: Boolean) {
        db.setIsPetRegistrationWentLive(visualized)
    }
}