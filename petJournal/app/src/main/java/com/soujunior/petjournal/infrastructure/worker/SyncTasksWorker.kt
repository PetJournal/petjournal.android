package com.soujunior.petjournal.infrastructure.worker

import android.content.Context
import android.util.Log
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
            Log.d("SyncTasksWorker", "🔄 [INICIANDO] Sincronização de tarefas (Month List)...")
            repository.listCurrentMonthScheduled(forceRequest = true)

            val tasksToSchedule = localDataSource.getTasksToSchedule()
            Log.d("SyncTasksWorker", "📊 [RESULTADO] Encontradas ${tasksToSchedule.size} tarefas para agendar no banco local.")

            tasksToSchedule.forEach { taskDto ->
                val domainTask = taskDto.toDomain()
                Log.d("SyncTasksWorker", "⚙️ [PROCESSANDO] Agendando ID: ${domainTask.id} | Título: ${domainTask.scheduler.title}")

                taskReminderScheduler.schedule(domainTask)

                taskDto.id?.let { id ->
                    Log.d("SyncTasksWorker", "✅ [STATUS] Marcando alarme como ativo para $id")
                    localDataSource.updateAlarmStatus(id, true)
                }
            }

            Result.success()
        } catch (e: Exception) {
            Log.e("SyncTasksWorker", "🚨 [ERRO] Falha na sincronização", e)
            Result.retry()
        }
    }
}
