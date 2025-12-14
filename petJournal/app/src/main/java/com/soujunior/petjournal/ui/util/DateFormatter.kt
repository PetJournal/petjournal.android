package com.soujunior.petjournal.ui.util

import android.content.Context
import androidx.annotation.StringRes
import com.soujunior.petjournal.R
import java.time.LocalDateTime
import java.time.Month
import java.time.format.DateTimeFormatter

class DateFormatter(private val context: Context) {
    private val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")

    fun formatDaily(dateString: String): String {
        val localDateTime = LocalDateTime.parse(dateString, inputFormatter)
        val day = localDateTime.dayOfMonth.toString().padStart(2, '0')

        val monthName = getMonthName(localDateTime.month)

        return "$day de $monthName"
    }

    @StringRes
    private fun getMonthStringRes(month: Month): Int {
        return when (month) {
            Month.JANUARY -> R.string.jan
            Month.FEBRUARY -> R.string.fev
            Month.MARCH -> R.string.mar
            Month.APRIL -> R.string.abr
            Month.MAY -> R.string.mai
            Month.JUNE -> R.string.jun
            Month.JULY -> R.string.jul
            Month.AUGUST -> R.string.ago
            Month.SEPTEMBER -> R.string.set
            Month.OCTOBER -> R.string.out
            Month.NOVEMBER -> R.string.nov
            Month.DECEMBER -> R.string.dez
        }
    }

    private fun getMonthName(month: Month): String {
        val stringResId = getMonthStringRes(month)
        return context.getString(stringResId)
    }
}
