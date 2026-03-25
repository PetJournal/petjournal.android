package com.petjournal.database.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.petjournal.database.database.dao.ApplicationInformationDao
import com.petjournal.database.database.dao.GuardianProfileDao
import com.petjournal.database.database.entity.ApplicationInformation
import com.petjournal.database.database.entity.GuardianProfile
import com.petjournal.database.database.entity.PetInformation
import com.petjournal.database.database.entity.PetRace
import com.petjournal.database.database.entity.PetSize

@Database(
    entities = [GuardianProfile::class, ApplicationInformation::class, PetInformation::class,
        PetSize::class, PetRace::class],
    version = 5, exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun guardianProfileDao(): GuardianProfileDao
    abstract fun applicationDao(): ApplicationInformationDao
}
