package com.petjournal.database.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.petjournal.database.database.entity.PetDetailsEntity

@Dao
interface PetDetailsDao {
    @Query("SELECT * FROM pet_details")
    suspend fun getAllPets(): List<PetDetailsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(pets: List<PetDetailsEntity>)

    @Query("DELETE FROM pet_details")
    suspend fun deleteAll()

    @Query("DELETE FROM pet_details WHERE id = :id")
    suspend fun deleteById(id: String)
}
