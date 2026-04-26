package com.petjournal.database.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.soujunior.domain.model.taskModel.SchedulerDTO

@Entity(tableName = "task")
data class TaskEntity(
    @PrimaryKey
    val id: String,
    val schedulerId: String?,
    val title: String,
    val description: String?,
    val note: String?,
    val start: String?,
    val end: String?,
    val isRecurrent: Boolean = false,
    val recurrenceType: String? = null,
    val daysOfWeek: String? = null,
    val daysOfMonth: String? = null,
    val tagId: String? = null,
    val scheduler: SchedulerDTO?,
    val isAlarmScheduled: Boolean = false
)
