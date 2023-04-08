package com.soujunior.petjournal.ui.util.mask

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText

const  val mask = "(XX) X XXXX-XXXX"
fun mobileNumberFilter(text: AnnotatedString): TransformedText {
    val trimmed = if (text.text.length >= 11) text.text.substring(0..10) else text.text

    val annotatedString = AnnotatedString.Builder().run {
        for (i in trimmed.indices) {
            if (i == 0) {
                append("(")
            }
            append(trimmed[i])
            when (i) {
                1 -> {
                    append(") ")
                }
                2 -> {
                    append(" ")
                }
                6 -> {
                    append("-")
                }
            }
        }
        pushStyle(SpanStyle(color = Color.LightGray))
        append(mask.takeLast(mask.length - length))
        toAnnotatedString()
    }
    val phoneNumberOffsetTranslator = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            if (offset <= 1) return offset + 1
            if (offset <= 2) return offset + 3
            if (offset in 3..6) return offset + 4
            if (offset in 7..11) return offset + 5
            return 16
        }

        override fun transformedToOriginal(offset: Int): Int {
            if (offset <= 1) return offset - 1
            if (offset <= 2) return offset - 3
            if (offset in 3..6) return offset - 4
            if (offset in 7..11) return offset - 5
            return 11
        }
    }
    return TransformedText(annotatedString, phoneNumberOffsetTranslator)
}