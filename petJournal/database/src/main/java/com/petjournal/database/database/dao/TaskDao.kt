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

    @Query("SELECT * FROM task WHERE start >= :startDate AND start <= :endDate")
    suspend fun getTasksInPeriod(startDate: String, endDate: String): List<TaskEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(tasks: List<TaskEntity>)

    @Query("DELETE FROM task")
    suspend fun deleteAll()
}
