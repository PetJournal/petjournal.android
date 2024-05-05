package com.soujunior.petjournal.ui.components.mask

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText

const val DATE_MASK = "DD/MM/AAAA"

fun formatDate(text: AnnotatedString): TransformedText {
    val trimmed = if (text.text.length >= 9) text.text.substring(0 until 8) else text.text

    val annotatedString = AnnotatedString.Builder().apply {
        for (i in trimmed.indices) {
            if (i == 2 || i == 4) {
                append("/")
            }
            append(trimmed[i])
        }
        pushStyle(SpanStyle(color = Color.LightGray))
        append(DATE_MASK.takeLast(maxOf(0, DATE_MASK.length - length)))
    }.toAnnotatedString()

    val dateOffsetTranslator = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            return when {
                offset <= 2 -> offset
                offset <= 4 -> offset + 1
                else -> offset + 2
            }
        }

        override fun transformedToOriginal(offset: Int): Int {
            return when {
                offset <= 2 -> offset
                offset <= 4 -> offset - 1
                else -> offset - 2
            }.coerceAtMost(text.length)
        }
    }
    return TransformedText(annotatedString, dateOffsetTranslator)
}