package com.soujunior.petjournal.infrastructure.reminder

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.soujunior.domain.model.taskModel.ScheduleDataModel
import com.soujunior.domain.repository.task.TaskReminderScheduler
import com.soujunior.petjournal.infrastructure.receiver.TaskAlarmReceiver
import java.time.OffsetDateTime

class AndroidTaskScheduler(private val context: Context) : TaskReminderScheduler {
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    override fun schedule(task: ScheduleDataModel) {
        if (task.id == null || task.start == null) return

        val intent =
            Intent(context, TaskAlarmReceiver::class.java).apply {
                putExtra("TASK_ID", task.id)
            }

        val pendingIntent =
            PendingIntent.getBroadcast(
                context,
                task.id.hashCode(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
            )

        val triggerAt =
            try {
                OffsetDateTime.parse(task.start).toInstant().toEpochMilli()
            } catch (e: Exception) {
                return
            }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                triggerAt,
                pendingIntent,
            )
        } else {
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                triggerAt,
                pendingIntent,
            )
        }
    }

    override fun cancel(task: ScheduleDataModel) {
        if (task.id == null) return

        val intent = Intent(context, TaskAlarmReceiver::class.java)
        val pendingIntent =
            PendingIntent.getBroadcast(
                context,
                task.id.hashCode(),
                intent,
                PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE,
            )

        if (pendingIntent != null) {
            alarmManager.cancel(pendingIntent)
        }
    }
}
