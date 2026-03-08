package com.soujunior.domain.model.mapper

import java.time.LocalDate
import java.time.Period
import java.time.ZonedDateTime

private fun parseAndCalculateAge(dateString: String): String {
    return try {
        val birthDate = ZonedDateTime.parse(dateString).toLocalDate()
        val currentDate = LocalDate.now()
        val period = Period.between(birthDate, currentDate)

        when {
            period.years > 1 -> "${period.years} anos"
            period.years == 1 -> "1 ano"
            period.months > 1 -> "${period.months} meses"
            period.months == 1 -> "1 mês"
            else -> "Menos de 1 mês"
        }
    } catch (e: Exception) {
        e.message.toString()
    }
}
