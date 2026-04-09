package com.soujunior.petjournal.ui.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

private const val ISO_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
private const val ISO_FORMAT_BACKUP = "yyyy-MM-dd'T'HH:mm:ss'Z'"
private val ptBrLocale = Locale("pt", "BR")

private fun String?.parseIsoDate(): Date? {
    if (this.isNullOrBlank()) return null
    return try {
        val format = SimpleDateFormat(ISO_FORMAT, Locale.getDefault())
        format.timeZone = TimeZone.getTimeZone("UTC")
        format.parse(this)
    } catch (e: Exception) {
        try {
            val format2 = SimpleDateFormat(ISO_FORMAT_BACKUP, Locale.getDefault())
            format2.timeZone = TimeZone.getTimeZone("UTC")
            format2.parse(this)
        } catch (ex: Exception) {
            null
        }
    }
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
    calendar.timeZone = TimeZone.getDefault()
    calendar.time = date

    calendar.firstDayOfWeek = Calendar.SUNDAY

    val weekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH)

    val startOfWeek = calendar.clone() as Calendar
    startOfWeek.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)

    val endOfWeek = calendar.clone() as Calendar
    endOfWeek.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY)

    val dayFormat = SimpleDateFormat("dd 'de' MMM", ptBrLocale)
    dayFormat.timeZone = TimeZone.getDefault()

    val startStr = dayFormat.format(startOfWeek.time).replaceFirstChar { it.uppercaseChar() }
    val endStr = dayFormat.format(endOfWeek.time)

    return "Semana $weekOfMonth: $startStr - $endStr"
}
