package com.petjournal.database.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.petjournal.database.database.entity.GuardianProfile
import com.petjournal.database.database.entity.PetRace
import com.petjournal.database.database.entity.PetSize
import com.petjournal.database.database.entity.PetInformation
import com.soujunior.domain.model.PetInformationModel
import kotlinx.coroutines.flow.Flow

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

    @Query("SELECT * FROM pet_information")
    suspend fun getAllPetInformation() : List<PetInformationModel>

    @Query("DELETE FROM pet_information WHERE id =:id")
    suspend fun deletePetInformation(id: Long)

    @Update
    fun updatePetInformation(petInformation: PetInformation)

    @Query("SELECT * FROM list_pet_sizes WHERE tag = :tag")
    suspend fun getListPetSizes(tag: String): List<PetSize>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListPetSizes(petSize: List<PetSize>)

    @Query("SELECT * FROM list_pet_races WHERE tag = :tag")
    suspend fun getListPetRaces(tag: String): List<PetRace>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListPetRaces(listPetSizes: List<PetRace>)
}
