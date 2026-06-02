package com.soujunior.petjournal.ui.util

import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

fun Modifier.adaptiveWidthForTitle(title: String): Modifier {
    val maxCharLength = 10
    return if (title.length >= maxCharLength) {
        this.wrapContentWidth()
    } else {
        this.width(115.dp)
    }
}
