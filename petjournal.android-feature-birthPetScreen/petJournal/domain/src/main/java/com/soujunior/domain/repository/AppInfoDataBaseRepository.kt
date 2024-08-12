package com.soujunior.domain.repository

interface AppInfoDataBaseRepository {
    suspend fun getIsPetRegistrationWentLive(): Boolean
    suspend fun setIsPetRegistrationWentLive(visualized: Boolean)
}