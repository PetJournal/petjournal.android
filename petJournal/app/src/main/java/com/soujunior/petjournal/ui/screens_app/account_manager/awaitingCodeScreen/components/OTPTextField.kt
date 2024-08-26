package com.soujunior.petjournal.ui.screens_app.account_manager.awaitingCodeScreen.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.components.AlertText
import com.soujunior.petjournal.ui.screens_app.account_manager.awaitingCodeScreen.AwaitingCodeFormEvent
import com.soujunior.petjournal.ui.screens_app.account_manager.awaitingCodeScreen.AwaitingCodeViewModel
import com.soujunior.petjournal.ui.theme.FredokaRegular
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun OTPTextField(
    modifier: Modifier = Modifier,
    otpCount: Int = 6,
    isError: Boolean = false,
    textValue: String,
    onEvent: (String) -> Unit,
    textError: List<String>?,
    viewModel: AwaitingCodeViewModel
) {
    LaunchedEffect(Unit) {
        if (textValue.length > otpCount) {
            throw IllegalArgumentException("O valor do texto OTP não deve ter mais de $otpCount caracteres")
        }
    }
    val resendCodeStyle = TextStyle(
        fontFamily = FontFamily(FredokaRegular),
        fontSize = 11.ssp,
        textDecoration = TextDecoration.Underline,
    )



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
    Spacer(modifier = Modifier.height(20.sdp))
    Box(
        modifier = Modifier
            .padding(top = 10.sdp, bottom = 15.sdp)
            .fillMaxWidth(),
        contentAlignment = Alignment.TopStart
    ) {
        Text(
            text = stringResource(R.string.txt_resend_code),
            style = resendCodeStyle,
            color = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.inverseSurface,
            modifier = Modifier.clickable {
                viewModel.onEvent(AwaitingCodeFormEvent.ResendCode)
            }
        )
    }
    textError?.forEach { AlertText(textMessage = it) }
    Spacer(modifier = Modifier.height(20.sdp))
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
            .offset(
                y = if (text.isNotEmpty()) {
                    if (index == text.length -1) 0.sdp else 20.sdp
                } else {
                    0.sdp
                }
            )
            .drawBehind {
                val stroke = Stroke(
                    width = 1.dp.toPx(),
                    pathEffect = PathEffect.dashPathEffect(
                        intervals = floatArrayOf(6.dp.toPx(), 6.dp.toPx(), 0f)
                    )
                )

                    drawRoundRect(
                        color = if (isError && text.length == 6) Color.Transparent
                        else if (text.length < 6 && text.isNotEmpty() && index == text.length -1) Color.Transparent
                        else colorBorder,
                        style = stroke,
                        cornerRadius = CornerRadius(10.dp.toPx())
                    )

            }
            .border(
                1.sdp,
                if (isError && text.length == 6) MaterialTheme.colorScheme.error
                else if (text.length < 6 && text.isNotEmpty() && index == text.length -1) MaterialTheme.colorScheme.primary
                else Color.Transparent,
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