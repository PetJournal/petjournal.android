package com.soujunior.petjournal.ui.util

fun String.capitalizeFirstLetter(): String {
    return this.lowercase().replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
}
