package com.soujunior.petjournal.ui.awaitingCodeScreen.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.soujunior.domain.entities.auth.AwaitingCodeModel
import com.soujunior.petjournal.ui.awaitingCodeScreen.AwaitingCodeScreenViewModel
import com.soujunior.petjournal.ui.awaitingCodeScreen.postOtpVerificationAwaitingCode
import com.soujunior.petjournal.ui.components.Button
import com.soujunior.petjournal.ui.states.States
import org.koin.androidx.compose.getViewModel

@Composable
fun Footer() {
    val awaitingCodeScreenViewModel: AwaitingCodeScreenViewModel = getViewModel()
    var isCodeFilled = false
    val email by States.localEmailState.current
    val otpFullCode by States.otpFullCode.current
    Spacer(modifier = Modifier.padding(32.dp))

    if (otpFullCode.isNotEmpty() && otpFullCode.isNotBlank() && otpFullCode.length == 6) {
        isCodeFilled = true
    }
    Button(
        submit = {
            postOtpVerificationAwaitingCode(
                AwaitingCodeModel(
                    codeOTP = otpFullCode,
                    email = email,
                ),
                awaitingCodeScreenViewModel
            )
        },
        enableButton = isCodeFilled
    )
    Spacer(modifier = Modifier.padding(8.dp))
    Text(
        text = "Dica: Caso não encontre o e-mail na sua caixa de entrada, verifique a pasta de Spam!",
        style = MaterialTheme.typography.subtitle1,
        textAlign = TextAlign.Center
    )
}