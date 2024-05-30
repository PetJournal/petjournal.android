package com.petjournal.database.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.petjournal.database.database.entity.GuardianProfile
import com.petjournal.database.database.entity.ListPetSizes
import com.petjournal.database.database.entity.PetInformation
import com.soujunior.domain.model.PetInformationModel

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

    @Update
    fun updatePetInformation(petInformation: PetInformation)

    @Query("SELECT * FROM list_pet_sizes WHERE id = :id")
    suspend fun getListPetSizes(id: String): ListPetSizes?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListPetSizes(listPetSizes: ListPetSizes): Long
}
