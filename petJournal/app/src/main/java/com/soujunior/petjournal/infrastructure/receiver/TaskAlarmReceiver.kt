package com.soujunior.petjournal.infrastructure.receiver

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.petjournal.database.database.dao.TaskDao
import com.petjournal.database.database.mapper.TaskMapper.toDomainModel
import com.soujunior.domain.repository.task.TaskReminderScheduler
import com.soujunior.petjournal.R
import com.soujunior.petjournal.navigation.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

class TaskAlarmReceiver : android.content.BroadcastReceiver() {
    private val taskDao: TaskDao by inject(TaskDao::class.java)
    private val taskReminderScheduler: TaskReminderScheduler by inject(TaskReminderScheduler::class.java)

    override fun onReceive(
        context: Context,
        intent: Intent,
    ) {
        val taskId = intent.getStringExtra("TASK_ID")
        if (taskId == null) {
            Log.e("TaskAlarmReceiver", "🚨 [ERRO] Intent recebida sem TASK_ID.")
            return
        }
        Log.d("TaskAlarmReceiver", "🔔 [RECEBIDO] Alarme disparado para a tarefa: $taskId")

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val taskEntity = taskDao.getTaskById(taskId)
                if (taskEntity == null) {
                    Log.e(
                        "TaskAlarmReceiver",
                        "🚨 [FALHA CRÍTICA] Tarefa $taskId não encontrada no banco de " +
                            "dados local no momento do disparo.",
                    )
                    return@launch
                }

                Log.d("TaskAlarmReceiver", "✅ [SUCESSO] Tarefa '${taskEntity.title}' recuperada do BD. Preparando notificação.")
                showNotification(
                    context,
                    taskEntity.title ?: "Lembrete do Pet",
                    taskEntity.description ?: "Você tem uma tarefa agora!",
                )

                if (taskEntity.isRecurrent) {
                    taskReminderScheduler.schedule(taskEntity.toDomainModel())
                } else {
                    taskEntity.id.let { id ->
                        taskDao.updateAlarmStatus(id, false)
                    }
                }
            } catch (e: Exception) {
                Log.e("TaskAlarmReceiver", "Erro ao processar alarme da tarefa $taskId", e)
            }
        }
    }

    private fun showNotification(
        context: Context,
        title: String,
        content: String,
    ) {
        val channelId = "pet_task_reminders"
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        Log.d("TaskAlarmReceiver", "⚙️ [NOTIFICAÇÃO] Construindo notificação. Título: '$title', ChannelID: '$channelId'")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val channel =
                NotificationChannel(
                    channelId,
                    "Lembretes de Tarefas",
                    NotificationManager.IMPORTANCE_HIGH,
                ).apply {
                    description = "Notificações para tarefas e lembretes dos seus pets"
                    enableLights(true)
                    lightColor = android.graphics.Color.BLUE
                    enableVibration(true)
                    setSound(notificationSound, null)
                }
            notificationManager.createNotificationChannel(channel)
        }

        val intent =
            Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
        val pendingIntent =
            PendingIntent.getActivity(
                context,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
            )

        val notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notification =
            NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.logo_blue)
                .setContentTitle(title)
                .setContentText(content)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_REMINDER)
                .setSound(notificationSound)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build()

        notificationManager.notify(System.currentTimeMillis().toInt(), notification)
    }
}
