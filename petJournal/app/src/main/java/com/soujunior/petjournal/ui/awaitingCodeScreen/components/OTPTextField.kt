package com.soujunior.petjournal.ui.awaitingCodeScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
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
fun OTPTextField (
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

    val darkTheme = isSystemInDarkTheme()
    val color =
        if (darkTheme)  Color(0xFFFF4081) else Color(0xFFB90063)

    val borderS = 2.5
    val isFocused = text.length == index
    val char = when {
        index == text.length -> "" // Por algo aqui como placeholder caso não tenha nada
        index > text.length -> ""
        else -> text[index].toString()
    }

    Text(
        modifier = Modifier
            .width(50.dp) // 52 e 64
            .height(62.5.dp)
            .background(
                when {
                    char.isNotEmpty() -> Color.White
                    else -> Color.LightGray
                }, RoundedCornerShape(8.dp)
            )
            .border(
                borderS.dp,
                if (isFocused) {
                    Color.Black
                } else {
                    when {
                        char.isEmpty() -> Color(0xFBAFD9DB)
                        else -> color
                    }
                }
                , RoundedCornerShape(8.dp)
            )
            .padding(12.dp, 12.dp),
        text = char,
        style = MaterialTheme.typography.h2,
        color = Color.Black,
        textAlign = TextAlign.Center
    )

}