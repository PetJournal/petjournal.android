package com.soujunior.petjournal.ui.awaitingCodeScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun OTPTextField(
    modifier: Modifier = Modifier,
    otpText: String,
    otpCount: Int = 6,
    onOtpTextChange: (String, Boolean) -> Unit
) {

    LaunchedEffect(Unit) {
        if (otpText.length > otpCount) {
            throw IllegalArgumentException("O valor do texto OTP não deve ter mais de $otpCount caracteres")
        }
    }

    BasicTextField(
        modifier = modifier,
        value = TextFieldValue(otpText, selection = TextRange(otpText.length)),
        onValueChange = {
            if (it.text.length <= otpCount) {
                onOtpTextChange.invoke(it.text, it.text.length == otpCount)
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
        decorationBox = {
            Row(horizontalArrangement = Arrangement.Center) {
                repeat(otpCount) { index ->
                    TextFieldSingleView(
                        index = index,
                        text = otpText
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }
    )
}

@Composable
private fun TextFieldSingleView(index: Int, text: String) {
    val isFocused = text.length == index
    val char = when {
        index == text.length -> ""
        index > text.length -> ""
        else -> text[index].toString()
    }

    Text(
        modifier = Modifier
            .width(50.dp)
            .height(62.5.dp)
            .background(
                when {
                    char.isNotEmpty() -> MaterialTheme.colorScheme.onSecondary
                    else -> MaterialTheme.colorScheme.outline
                }, MaterialTheme.shapes.small            )
            .border(
                2.5.dp,
                if (isFocused) {
                    MaterialTheme.colorScheme.onBackground
                } else {
                    when {
                        char.isEmpty() ->  MaterialTheme.colorScheme.outlineVariant
                        else -> MaterialTheme.colorScheme.primary
                    }
                }, RoundedCornerShape(8.dp)
            )
            .padding(12.dp, 12.dp),
        text = char,
        style = MaterialTheme.typography.displayMedium,
        color = Color.Black,
        textAlign = TextAlign.Center
    )
}