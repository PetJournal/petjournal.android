package com.petjournal.database.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tag")
data class TagEntity(
    @PrimaryKey
    val id: String,
    val guardianId: String?,
    val name: String?,
    val color: String?
)
