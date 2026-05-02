package com.soujunior.domain.use_case.util

import java.time.DayOfWeek
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters

/**
 * Tipo de recorrência para o cálculo de datas de tarefas.
 */
enum class TaskPeriod {
    DAILY,
    WEEKLY,
    MONTHLY
}

/**
 * Utilitário responsável por cálculos de datas e prazos para o domínio de tarefas.
 * Centraliza a regra de negócio para definição de limites temporais de agendamentos.
 */
object TaskDateCalculator {

    /**
     * Calcula a data de término padrão (endAt) para uma tarefa recorrente.
     * 
     * A regra de negócio define que a data de término deve ser a data da 
     * primeira ocorrência válida (baseada na recorrência e na data de início) 
     * somada a 2 anos no futuro.
     *
     * @param startAtIso A data de início/referência no formato ISO Instant (ex: "2023-10-27T10:00:00Z").
     * @param period O tipo de recorrência da tarefa ([TaskPeriod]).
     * @param daysOfWeek Lista de inteiros representando os dias da semana (0=Domingo, 1=Segunda, ..., 6=Sábado).
     *                   Utilizado quando o período é [TaskPeriod.WEEKLY].
     * @param dayOfMonth O dia do mês para a recorrência.
     *                   Utilizado quando o período é [TaskPeriod.MONTHLY].
     * @return A string da data de término calculada no formato ISO Instant.
     */
    fun calculateDefaultEndAt(
        startAtIso: String,
        period: TaskPeriod,
        daysOfWeek: List<Int>? = null,
        dayOfMonth: Int? = null
    ): String {
        val startDateTime = ZonedDateTime.parse(startAtIso)
        var nextOccurrence = startDateTime

        when (period) {
            TaskPeriod.WEEKLY -> {
                if (!daysOfWeek.isNullOrEmpty()) {
                    nextOccurrence = findNextWeeklyOccurrence(startDateTime, daysOfWeek)
                }
            }
            TaskPeriod.MONTHLY -> {
                if (dayOfMonth != null) {
                    nextOccurrence = findNextMonthlyOccurrence(startDateTime, dayOfMonth)
                }
            }
            TaskPeriod.DAILY -> {
                // Para tarefas diárias, a data de referência inicial já é considerada a primeira ocorrência
                nextOccurrence = startDateTime
            }
        }

        // Regra de negócio: Adicionar 2 anos à primeira ocorrência encontrada
        val endAtDateTime = nextOccurrence.plusYears(2)
        
        return endAtDateTime.format(DateTimeFormatter.ISO_INSTANT)
    }

    /**
     * Identifica a próxima ocorrência semanal válida a partir da data de início.
     * Se a data de início coincidir com um dos dias da semana selecionados, ela é retornada.
     */
    private fun findNextWeeklyOccurrence(start: ZonedDateTime, days: List<Int>): ZonedDateTime {
        // Mapeamento: 0 (UI/DTO) -> SUNDAY (java.time), 1-6 -> MONDAY-SATURDAY
        val targetDays = days.map { if (it == 0) DayOfWeek.SUNDAY else DayOfWeek.of(it) }
        
        val currentDay = start.dayOfWeek
        
        if (targetDays.contains(currentDay)) {
            return start
        }

        return targetDays
            .map { start.with(TemporalAdjusters.next(it)) }
            .minByOrNull { it.toEpochSecond() } ?: start
    }

    /**
     * Identifica a próxima ocorrência mensal baseada no dia do mês.
     * Lida com meses curtos ajustando para o último dia disponível quando necessário.
     */
    private fun findNextMonthlyOccurrence(start: ZonedDateTime, dayOfMonth: Int): ZonedDateTime {
        val currentDay = start.dayOfMonth
        
        return if (currentDay <= dayOfMonth) {
            try {
                start.withDayOfMonth(dayOfMonth)
            } catch (e: Exception) {
                // Se o dia não existe no mês atual (ex: 31 de fevereiro), usa o último dia do mês
                start.with(TemporalAdjusters.lastDayOfMonth())
            }
        } else {
            val nextMonth = start.plusMonths(1)
            try {
                nextMonth.withDayOfMonth(dayOfMonth)
            } catch (e: Exception) {
                nextMonth.with(TemporalAdjusters.lastDayOfMonth())
            }
        }
    }
}
