package com.soujunior.domain.repository

interface AppInfoDataBase {
    suspend fun getIsPetRegistrationWentLive(): Boolean
    suspend fun setIsPetRegistrationWentLive(visualized: Boolean)
}