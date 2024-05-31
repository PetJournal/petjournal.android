package com.petjournal.database.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.petjournal.database.converter.ConverterListPetRaces
import com.petjournal.database.converter.ConverterListPetSizes
import com.petjournal.database.database.dao.ApplicationInformationDao
import com.petjournal.database.database.dao.GuardianProfileDao
import com.petjournal.database.database.entity.ApplicationInformation
import com.petjournal.database.database.entity.GuardianProfile
import com.petjournal.database.database.entity.ListPetRaces
import com.petjournal.database.database.entity.ListPetSizes
import com.petjournal.database.database.entity.PetInformation

@Database(
    entities = [GuardianProfile::class, ApplicationInformation::class, PetInformation::class,
        ListPetSizes::class, ListPetRaces::class],
    version = 3, exportSchema = false
)

@TypeConverters(ConverterListPetSizes::class, ConverterListPetRaces::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun guardianProfileDao(): GuardianProfileDao
    abstract fun applicationDao(): ApplicationInformationDao
}
