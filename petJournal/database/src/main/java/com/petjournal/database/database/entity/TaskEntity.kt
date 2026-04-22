package com.petjournal.database.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.soujunior.domain.model.taskModel.SchedulerDTO

@Entity(tableName = "task")
data class TaskEntity(
    @PrimaryKey
    val id: String,
    val schedulerId: String?,
    val start: String?,
    val end: String?,
    val scheduler: SchedulerDTO?
)
