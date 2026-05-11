package com.soujunior.domain.repository.appinfo

interface AppInfoDatabase {
    suspend fun getIsPetRegistrationWentLive(): Boolean
    suspend fun setIsPetRegistrationWentLive(visualized: Boolean)
}
