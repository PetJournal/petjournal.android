package com.petjournal.database.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.petjournal.database.database.dao.GuardianProfileDao
import com.petjournal.database.database.entity.GuardianProfile

@Database(entities = [GuardianProfile::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun guardianProfileDao(): GuardianProfileDao
}
