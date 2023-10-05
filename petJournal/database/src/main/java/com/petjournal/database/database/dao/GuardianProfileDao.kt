package com.petjournal.database.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.petjournal.database.database.entity.GuardianProfile

@Dao
interface GuardianProfileDao {
    @Insert
    suspend fun insertProfile(profile: GuardianProfile): Long

    @Query("SELECT * FROM guardian_profile WHERE id = :id")
    suspend fun getProfile(id: Int): GuardianProfile?

    @Query("DELETE FROM guardian_profile")
    suspend fun deleteAllProfiles()
}
