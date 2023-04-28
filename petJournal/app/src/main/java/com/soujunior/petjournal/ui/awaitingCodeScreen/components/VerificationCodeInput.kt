package com.soujunior.petjournal.ui.awaitingCodeScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun VerificationCodeInput() {
    var otpValue by remember { mutableStateOf("") }
    val borderS = 2.5

    BasicTextField(
        value = otpValue,
        onValueChange = {
            if (it.length <= 6) {
                otpValue = it
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.NumberPassword
        ),
        decorationBox = {

            Row(horizontalArrangement = Arrangement.Center) {

                repeat(6) {
                    val char = when {
                        it >= otpValue.length -> ""
                        else -> otpValue[it].toString()
                    }
                    val isFocused = otpValue.length == it
                    Text(
                        modifier = Modifier
                            .width(56.dp)
                            .height(70.dp)
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
                                        else -> Color(0xFB9A0963)
                                    }
                                }, RoundedCornerShape(8.dp)
                            )
                            .padding(12.dp, 12.dp),
                        text = char,
                        style = MaterialTheme.typography.h4,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )
                    if (it < 5) {
                        Spacer(modifier = Modifier.width(12.dp))
                    }

                }

            }
        }
    )
    Text(
        text = "Reenviar código?",
        modifier = Modifier
            .padding(start = 12.dp, top = 12.dp)
    )
}