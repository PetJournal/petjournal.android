package com.petjournal.database.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.petjournal.database.database.entity.ApplicationInformation

@Dao
interface ApplicationInformationDao {
    @Insert
    suspend fun insertInformation(information: ApplicationInformation)

    @Query("UPDATE information SET isPetRegistrationWentLive = :isLive WHERE id = :profileId")
    suspend fun setPetRegistrationWentLive(profileId: Int, isLive: Boolean)

    @Query("SELECT isPetRegistrationWentLive FROM information WHERE id = :profileId")
    suspend fun getPetRegistrationWentLive(profileId: Int): Boolean
}