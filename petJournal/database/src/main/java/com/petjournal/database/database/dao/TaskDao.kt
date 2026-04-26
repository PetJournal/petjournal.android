package com.petjournal.database.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.petjournal.database.database.entity.TaskEntity

@Dao
interface TaskDao {
    @Query("SELECT * FROM task")
    suspend fun getAllTasks(): List<TaskEntity>

    @Query("""
        SELECT * FROM task 
        WHERE (start >= :startDate AND start <= :endDate) 
        OR (isRecurrent = 1 AND (
            recurrenceType = 'DAILY' 
            OR (recurrenceType = 'WEEKLY' AND daysOfWeek LIKE '%' || :dayOfWeek || '%')
            OR (recurrenceType = 'MONTHLY' AND daysOfMonth LIKE '%' || :dayOfMonth || '%')
        ))
    """)
    suspend fun getTasksInPeriod(startDate: String, endDate: String, dayOfWeek: String, dayOfMonth: String): List<TaskEntity>

    @Query("SELECT * FROM task WHERE isAlarmScheduled = 0")
    suspend fun getTasksToSchedule(): List<TaskEntity>

    @Query("SELECT * FROM task WHERE id = :id")
    suspend fun getTaskById(id: String): TaskEntity?

    @Query("SELECT * FROM task WHERE id IN (:ids)")
    suspend fun getTasksByIds(ids: List<String>): List<TaskEntity>

    @Query("UPDATE task SET isAlarmScheduled = :isScheduled WHERE id = :id")
    suspend fun updateAlarmStatus(id: String, isScheduled: Boolean)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(tasks: List<TaskEntity>)

    @Query("DELETE FROM task")
    suspend fun deleteAll()
}
