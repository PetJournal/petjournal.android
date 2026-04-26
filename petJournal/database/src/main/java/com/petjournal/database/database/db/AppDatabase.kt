package com.petjournal.database.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.petjournal.database.converter.RoomConverters
import com.petjournal.database.database.dao.ApplicationInformationDao
import com.petjournal.database.database.dao.GuardianProfileDao
import com.petjournal.database.database.dao.PetDetailsDao
import com.petjournal.database.database.dao.TagDao
import com.petjournal.database.database.dao.TaskDao
import com.petjournal.database.database.entity.ApplicationInformation
import com.petjournal.database.database.entity.GuardianProfile
import com.petjournal.database.database.entity.PetInformation
import com.petjournal.database.database.entity.PetRace
import com.petjournal.database.database.entity.PetSize
import com.petjournal.database.database.entity.PetDetailsEntity
import com.petjournal.database.database.entity.TagEntity
import com.petjournal.database.database.entity.TaskEntity

@Database(
    entities = [
        GuardianProfile::class, ApplicationInformation::class, PetInformation::class,
        PetSize::class, PetRace::class, PetDetailsEntity::class, TagEntity::class, TaskEntity::class
    ],
    version = 9, exportSchema = false
)
@TypeConverters(RoomConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun guardianProfileDao(): GuardianProfileDao
    abstract fun applicationDao(): ApplicationInformationDao
    abstract fun petDetailsDao(): PetDetailsDao
    abstract fun tagDao(): TagDao
    abstract fun taskDao(): TaskDao
}
