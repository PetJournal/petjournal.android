package com.soujunior.petjournal.ui.util

import java.time.LocalDateTime
import java.time.Month
import java.time.format.DateTimeFormatter

object DateFormatter{

    private val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")

    fun formatDaily(dateString: String) : String{
        val localDateTime = LocalDateTime.parse(dateString, inputFormatter)
        val day = localDateTime.dayOfMonth.toString().padStart(2, '0')
        val monthName = getMonthName(localDateTime.monthValue)
        return "$day de $monthName"
    }

    private fun getMonthName(month: Int): String{
        return when(month){
            1 -> "Jan"
            2 -> "Fev"
            3 -> "Mar"
            4 -> "Abr"
            5 -> "Mai"
            6 -> "Jun"
            7 -> "Jul"
            8 -> "Ago"
            9 -> "Set"
            10 -> "Out"
            11 -> "Nov"
            12 -> "Dez"
            else -> ""
        }
    }

}
