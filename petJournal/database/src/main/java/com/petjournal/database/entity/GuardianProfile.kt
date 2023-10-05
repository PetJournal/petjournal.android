package com.petjournal.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "guardian_profile")
data class GuardianProfile(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val firstName: String? = null,
    val lastName: String? = null,
    val email: String? = null,
    val phone: String? = null
)
