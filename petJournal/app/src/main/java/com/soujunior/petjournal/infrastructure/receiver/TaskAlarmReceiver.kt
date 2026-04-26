package com.soujunior.petjournal.infrastructure.receiver

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.petjournal.database.database.dao.TaskDao
import com.soujunior.petjournal.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

class TaskAlarmReceiver : BroadcastReceiver() {
    private val taskDao: TaskDao by inject(TaskDao::class.java)

    override fun onReceive(
        context: Context,
        intent: Intent,
    ) {
        val taskId = intent.getStringExtra("TASK_ID") ?: return

        CoroutineScope(Dispatchers.IO).launch {
            val task = taskDao.getTaskById(taskId)
            task?.let {
                showNotification(
                    context,
                    it.scheduler?.title ?: "Lembrete do Pet",
                    it.scheduler?.description ?: "Você tem uma tarefa agora!",
                )
            }
        }
    }

    private fun showNotification(
        context: Context,
        title: String,
        message: String,
    ) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "pet_task_reminders"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(
                    channelId,
                    "Lembretes de Tarefas",
                    NotificationManager.IMPORTANCE_HIGH,
                )
            notificationManager.createNotificationChannel(channel)
        }

        val notification =
            NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.ic_pet) // Certifique-se que este ícone existe
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .build()

        notificationManager.notify(title.hashCode(), notification)
    }
}
