package com.soujunior.petjournal.ui.util

import java.util.Date
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun calculateAge(dateString: String): Pair<Int, Int> {
    val birthDate = Calendar.getInstance().apply {
        timeInMillis = convertToTimestamp(dateString)
    }
    val currentDate = Calendar.getInstance()

    val years = currentDate.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR)
    var months = currentDate.get(Calendar.MONTH) - birthDate.get(Calendar.MONTH)

    if (months < 0) {
        months += 12
        birthDate.add(Calendar.YEAR, 1)
    }

    if (currentDate.before(birthDate)) {
        months -= 1
    }

    return Pair(years, months)
}

fun formatAge(age: Pair<Int, Int>): String {
    val (years, months) = age
    return when {
        years == 0 -> "$months ${if (months == 1) "mês" else "meses"}"
        months == 0 -> "$years ${if (years == 1) "ano" else "anos"}"
        else -> "$years ${if (years == 1) "ano" else "anos"} e $months meses"
    }
}

private fun convertToTimestamp(dateString: String): Long {
    val dateFormat = SimpleDateFormat("ddMMyyyy", Locale.getDefault())
    val date: Date? = dateFormat.parse(dateString)
    return date?.time ?: throw IllegalArgumentException("Invalid date format")
}