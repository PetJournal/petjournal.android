package com.soujunior.petjournal.infrastructure.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.soujunior.domain.mapper.Mapper.toDomain
import com.soujunior.domain.repository.api.Repository
import com.soujunior.domain.repository.database.LocalDataSource
import com.soujunior.domain.repository.task.TaskReminderScheduler
import org.koin.java.KoinJavaComponent.inject

class SyncTasksWorker(
    context: Context,
    workerParams: WorkerParameters,
) : CoroutineWorker(context, workerParams) {
    private val repository: Repository by inject(Repository::class.java)
    private val localDataSource: LocalDataSource by inject(LocalDataSource::class.java)
    private val taskReminderScheduler: TaskReminderScheduler by inject(TaskReminderScheduler::class.java)

    override suspend fun doWork(): Result {
        return try {
            // Sincroniza tarefas do mês atual
            repository.listCurrentMonthScheduled(forceRequest = true)

            // Busca tarefas que ainda não foram agendadas no sistema de alarmes
            val tasksToSchedule = localDataSource.getTasksToSchedule()

            tasksToSchedule.forEach { taskDto ->
                taskReminderScheduler.schedule(taskDto.toDomain())
                taskDto.id?.let { id ->
                    localDataSource.updateAlarmStatus(id, true)
                }
            }

            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}
