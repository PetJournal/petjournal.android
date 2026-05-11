package com.soujunior.petjournal.ui.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

private const val ISO_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
private const val ISO_FORMAT_BACKUP = "yyyy-MM-dd'T'HH:mm:ss'Z'"
private const val ISO_FORMAT_ROOM = "yyyy-MM-dd'T'HH:mm:ss"
private val ptBrLocale = Locale("pt", "BR")

private fun String?.parseIsoDate(): Date? {
    if (this.isNullOrBlank()) return null
    val formats = listOf(ISO_FORMAT, ISO_FORMAT_BACKUP, ISO_FORMAT_ROOM)

    for (pattern in formats) {
        try {
            val format = SimpleDateFormat(pattern, Locale.getDefault())
            format.timeZone = if (pattern.endsWith("'Z'")) TimeZone.getTimeZone("UTC") else TimeZone.getDefault()
            return format.parse(this)
        } catch (e: Exception) {
            continue
        }
    }
    return null
}

fun String?.toCardFormat(): String {
    val date = this.parseIsoDate() ?: return this ?: ""
    val outputFormat = SimpleDateFormat("dd/MM/yyyy - HH:mm", ptBrLocale)
    outputFormat.timeZone = TimeZone.getDefault()
    return outputFormat.format(date)
}

fun String?.toDailyGroupFormat(): String {
    val date = this.parseIsoDate() ?: return this ?: ""
    val outputFormat = SimpleDateFormat("dd 'de' MMM", ptBrLocale)
    outputFormat.timeZone = TimeZone.getDefault()
    return outputFormat.format(date).replaceFirstChar { it.uppercaseChar() }
}

fun String?.toMonthlyGroupFormat(): String {
    val date = this.parseIsoDate() ?: return this ?: ""
    val outputFormat = SimpleDateFormat("MMMM, yyyy", ptBrLocale)
    outputFormat.timeZone = TimeZone.getDefault()
    return outputFormat.format(date).replaceFirstChar { it.uppercaseChar() }
}

fun String?.toWeeklyGroupFormat(): String {
    val date = this.parseIsoDate() ?: return this ?: ""

    val calendar = Calendar.getInstance(ptBrLocale)
    calendar.timeZone = TimeZone.getTimeZone("UTC") // Usar UTC para o cálculo inicial
    calendar.time = date

    // Ajustar para o fuso local para exibição
    val localCalendar = Calendar.getInstance(ptBrLocale)
    localCalendar.time = date

    localCalendar.firstDayOfWeek = Calendar.SUNDAY
    val weekOfYear = localCalendar.get(Calendar.WEEK_OF_YEAR)

    val startOfWeek = localCalendar.clone() as Calendar
    startOfWeek.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)

    val endOfWeek = localCalendar.clone() as Calendar
    endOfWeek.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY)

    val dayFormat = SimpleDateFormat("dd 'de' MMM", ptBrLocale)
    dayFormat.timeZone = TimeZone.getDefault()

    val startStr = dayFormat.format(startOfWeek.time).replaceFirstChar { it.uppercaseChar() }
    val endStr = dayFormat.format(endOfWeek.time).replaceFirstChar { it.uppercaseChar() }

    return "Semana $weekOfYear: $startStr - $endStr"
}
