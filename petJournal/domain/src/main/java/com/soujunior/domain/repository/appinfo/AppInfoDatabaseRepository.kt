package com.soujunior.domain.repository.appinfo

interface AppInfoDatabaseRepository {
    suspend fun getIsPetRegistrationWentLive(): Boolean
    suspend fun setIsPetRegistrationWentLive(visualized: Boolean)
}
