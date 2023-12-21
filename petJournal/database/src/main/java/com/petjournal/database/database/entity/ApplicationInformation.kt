package com.petjournal.database.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "information")
data class ApplicationInformation(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val isPetRegistrationWentLive: Boolean = false,
)
