package com.petjournal.database.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.petjournal.database.database.dao.ApplicationInformationDao
import com.petjournal.database.database.dao.GuardianProfileDao
import com.petjournal.database.database.entity.ApplicationInformation
import com.petjournal.database.database.entity.GuardianProfile

@Database(entities = [GuardianProfile::class, ApplicationInformation::class], version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun guardianProfileDao(): GuardianProfileDao
    abstract fun applicationDao(): ApplicationInformationDao
}
