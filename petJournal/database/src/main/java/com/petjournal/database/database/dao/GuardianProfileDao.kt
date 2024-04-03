package com.petjournal.database.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.petjournal.database.database.entity.GuardianProfile
import com.petjournal.database.database.entity.PetInformation
import com.soujunior.domain.model.PetInformationModel
import com.soujunior.domain.use_case.base.DataResult

@Dao
interface GuardianProfileDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE) //Caso o usuario já exista, será substituido
    suspend fun insertProfile(profile: GuardianProfile): Long

    @Query("SELECT * FROM guardian_profile WHERE id = :id")
    suspend fun getProfile(id: Int): GuardianProfile?

    @Query("DELETE FROM guardian_profile")
    suspend fun deleteAllProfiles()

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertPetInformation(petInformation: PetInformation): Long

    @Query("SELECT * FROM pet_information WHERE id = :id")
    suspend fun getPetInformation(id: Long): PetInformationModel
}
