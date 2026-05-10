package com.soujunior.petjournal.infrastructure.reminder

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.soujunior.domain.model.taskModel.ScheduleDataModel
import com.soujunior.domain.repository.task.TaskReminderScheduler
import com.soujunior.petjournal.infrastructure.receiver.TaskAlarmReceiver
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.temporal.TemporalAdjusters

class AndroidTaskScheduler(private val context: Context) : TaskReminderScheduler {
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    override fun schedule(task: ScheduleDataModel) {
        if (task.id == null || task.start == null) {
            Log.e("TaskScheduler", "🚨 [CANCELADO] Tarefa inválida: ID=${task.id}, Start=${task.start}")
            return
        }

        Log.d("TaskScheduler", "📌 [INICIANDO] Agendamento: ${task.scheduler.title} (ID: ${task.id})")
        Log.d("TaskScheduler", "   Data original (ISO): ${task.start}")
        Log.d(
            "TaskScheduler",
            "   Detalhes: daily=${task.scheduler.daily}, daysOfWeek=${task.scheduler.daysOfWeek}, " +
                "daysOfMonth=${task.scheduler.daysOfMonth}",
        )

        val triggerAt = calculateNextOccurrence(task)
        if (triggerAt == null) {
            Log.e("TaskScheduler", "🚨 [ERRO] Próxima ocorrência nula para ${task.id}. Pode estar no passado?")
            return
        }

        val triggerInstant = java.time.Instant.ofEpochMilli(triggerAt)
        Log.d(
            "TaskScheduler",
            "✅ [CALCULADO] Próximo disparo em: $triggerInstant (Local: " +
                "${java.time.LocalDateTime.ofInstant(triggerInstant, ZoneId.systemDefault())})",
        )

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

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                if (alarmManager.canScheduleExactAlarms()) {
                    Log.d("TaskScheduler", "✅ [PERMISSÃO] SCHEDULE_EXACT_ALARM concedida.")
                    alarmManager.setExactAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        triggerAt,
                        pendingIntent,
                    )
                    Log.d("TaskScheduler", "🚀 [AGENDADO] Alarme EXATO (S+) para ${task.id}")
                } else {
                    Log.w(
                        "TaskScheduler",
                        "⚠️ [AVISO] Permissão SCHEDULE_EXACT_ALARM negada. Sem permissão de alarme exato. " +
                            "Usando aproximado.",
                    )
                    alarmManager.setAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        triggerAt,
                        pendingIntent,
                    )
                    Log.w("TaskScheduler", "⚠️ [AVISO] Sem permissão de alarme exato. Usando aproximado.")
                }
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    triggerAt,
                    pendingIntent,
                )
                Log.d("TaskScheduler", "🚀 [AGENDADO] Alarme EXATO (M-R) para ${task.id}")
            } else {
                alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    triggerAt,
                    pendingIntent,
                )
                Log.d("TaskScheduler", "🚀 [AGENDADO] Alarme EXATO (Legacy) para ${task.id}")
            }
        } catch (e: SecurityException) {
            Log.e("TaskScheduler", "🚨 [ERRO] SecurityException ao agendar. Usando fallback.", e)
            alarmManager.set(
                AlarmManager.RTC_WAKEUP,
                triggerAt,
                pendingIntent,
            )
        } catch (e: Exception) {
            Log.e("TaskScheduler", "🚨 [ERRO] Falha crítica ao agendar alarme", e)
        }
    }

    private fun calculateNextOccurrence(task: ScheduleDataModel): Long? {
        return try {
            val startStr = task.start!!
            val zoneId = ZoneId.systemDefault()

            // Tenta parsear a data. Se tiver 'Z', tratamos como UTC. Se não, como Local.
            val startInstant =
                try {
                    if (startStr.contains("Z") || startStr.contains("+")) {
                        val parsed = java.time.Instant.parse(startStr)
                        Log.d(
                            "TaskScheduler",
                            "   [PARSE] Data com fuso horário detectado. Instant: $parsed, Local: " +
                                "${java.time.LocalDateTime.ofInstant(parsed, zoneId)}",
                        )
                        parsed
                    } else {
                        // Se não tem timezone, assume que é o horário local que o usuário escolheu
                        val normalizedStr = if (startStr.contains("T")) startStr else "${startStr}T00:00:00"
                        val parsed = LocalDateTime.parse(normalizedStr).atZone(zoneId).toInstant()
                        Log.d("TaskScheduler", "   [PARSE] Data sem fuso horário detectado. Assumindo Local. Instant: $parsed")
                        parsed
                    }
                } catch (e: Exception) {
                    Log.e("TaskScheduler", "🚨 [ERRO PARSE] Falha ao parsear data '$startStr': ${e.message}")
                    return null
                }

            val nowInstant = java.time.Instant.now()

            // Se for tarefa única (One-Off)
            if (task.scheduler.daily != true && task.scheduler.daysOfWeek.isNullOrEmpty() && task.scheduler.daysOfMonth.isNullOrEmpty()) {
                return if (startInstant.isAfter(nowInstant)) {
                    startInstant.toEpochMilli()
                } else {
                    Log.w(
                        "TaskScheduler",
                        "   [INFO] Tarefa única ${task.id} está no passado. (Agendada: " +
                            "$startInstant, Agora: $nowInstant)",
                    )
                    null
                }
            }

            // Para tarefas recorrentes, calculamos a próxima ocorrência baseada no horário (LocalTime)
            val startDateTimeLocal = LocalDateTime.ofInstant(startInstant, zoneId)
            val taskTime = startDateTimeLocal.toLocalTime()
            val nowLocal = LocalDateTime.now(zoneId)

            var nextOccurrence: LocalDateTime? = null

            if (task.scheduler.daily == true) {
                nextOccurrence = LocalDateTime.of(nowLocal.toLocalDate(), taskTime)
                if (nextOccurrence.isBefore(nowLocal)) {
                    nextOccurrence = nextOccurrence.plusDays(1)
                }
            } else if (!task.scheduler.daysOfWeek.isNullOrEmpty()) {
                nextOccurrence = findNextDayOfWeek(nowLocal, taskTime, task.scheduler.daysOfWeek!!)
            } else if (!task.scheduler.daysOfMonth.isNullOrEmpty()) {
                nextOccurrence = findNextDayOfMonth(nowLocal, taskTime, task.scheduler.daysOfMonth!!)
            }

            nextOccurrence?.atZone(zoneId)?.toInstant()?.toEpochMilli()
        } catch (e: Exception) {
            Log.e("TaskScheduler", "   [ERRO] Falha no cálculo: ${e.message}")
            null
        }
    }

    private fun findNextDayOfWeek(
        now: LocalDateTime,
        time: LocalTime,
        days: List<Int>,
    ): LocalDateTime {
        val mappedDays = days.map { if (it == 0) 7 else it }
        var minNext: LocalDateTime? = null

        for (day in mappedDays) {
            val targetDay = DayOfWeek.of(day)
            var next = now.with(TemporalAdjusters.nextOrSame(targetDay)).with(time)

            if (next.isBefore(now)) {
                next = now.with(TemporalAdjusters.next(targetDay)).with(time)
            }

            if (minNext == null || next.isBefore(minNext)) {
                minNext = next
            }
        }
        return minNext ?: now.plusDays(7).with(time)
    }

    private fun findNextDayOfMonth(
        now: LocalDateTime,
        time: LocalTime,
        days: List<Int>,
    ): LocalDateTime {
        var minNext: LocalDateTime? = null

        for (day in days) {
            var nextDate =
                try {
                    LocalDate.now().withDayOfMonth(day)
                } catch (e: Exception) {
                    LocalDate.now().with(TemporalAdjusters.lastDayOfMonth())
                }

            var next = LocalDateTime.of(nextDate, time)
            if (next.isBefore(now)) {
                next = next.plusMonths(1)
            }

            if (minNext == null || next.isBefore(minNext)) {
                minNext = next
            }
        }
        return minNext ?: now.plusMonths(1).with(time)
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
            Log.d("TaskScheduler", "🗑️ [CANCELADO] Removendo alarme para tarefa ${task.id}")
            alarmManager.cancel(pendingIntent)
        }
    }
}
