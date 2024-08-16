package com.soujunior.petjournal.ui.screens_app.account_manager.awaitingCodeScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.soujunior.petjournal.ui.components.AlertText
import ir.kaaveh.sdpcompose.sdp

@Composable
fun OTPTextField(
    modifier: Modifier = Modifier,
    otpCount: Int = 6,
    isError: Boolean = false,
    textValue: String,
    onEvent: (String) -> Unit,
    textError: List<String>?,
) {
    LaunchedEffect(Unit) {
        if (textValue.length > otpCount) {
            throw IllegalArgumentException("O valor do texto OTP não deve ter mais de $otpCount caracteres")
        }
    }

    BasicTextField(
        modifier = modifier,
        value = TextFieldValue(textValue, selection = TextRange(textValue.length)),
        onValueChange = {
            if (it.text.length <= otpCount) {
                onEvent.invoke(it.text)
                onEvent(it.text)
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
        decorationBox = {
            Row(horizontalArrangement = Arrangement.Center) {
                repeat(otpCount) { index ->
                    TextFieldSingleView(
                        isError = isError,
                        index = index,
                        text = textValue
                    )
                    Spacer(modifier = Modifier.width(8.sdp))
                }
            }
        }
    )
    textError?.forEach { AlertText(textMessage = it) }
}

@Composable
private fun TextFieldSingleView(index: Int, text: String, isError: Boolean = false) {
    val colorBorder = MaterialTheme.colorScheme.outline
    val char = when {
        index == text.length -> ""
        index > text.length -> ""
        else -> text[index].toString()
    }

    Text(
        modifier = Modifier
            .width(35.sdp)
            .height(35.sdp)
            .drawBehind {
                val stroke = Stroke(
                    width = 1.dp.toPx(),
                    pathEffect = PathEffect.dashPathEffect(
                        intervals = floatArrayOf(6.dp.toPx(), 6.dp.toPx(), 0f)
                    )
                )
                drawRoundRect(
                    color = if (isError) Color.Transparent else colorBorder,
                    style = stroke,
                    cornerRadius = CornerRadius(10.dp.toPx())
                )

            }
            .border(
                1.sdp,
                if (isError) MaterialTheme.colorScheme.error else Color.Transparent,
                shape = RoundedCornerShape(10.sdp)
            )
            .clip(RoundedCornerShape(10.sdp))
            .padding(10.sdp),
        text = char,
        style = MaterialTheme.typography.titleMedium,
        color = Color.Black,
        textAlign = TextAlign.Center
    )
}