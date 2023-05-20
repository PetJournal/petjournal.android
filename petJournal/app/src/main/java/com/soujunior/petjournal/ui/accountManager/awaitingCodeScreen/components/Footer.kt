package com.soujunior.petjournal.ui.accountManager.awaitingCodeScreen.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.soujunior.domain.entities.auth.AwaitingCodeModel
import com.soujunior.petjournal.ui.accountManager.awaitingCodeScreen.AwaitingCodeScreenViewModel
import com.soujunior.petjournal.ui.accountManager.awaitingCodeScreen.postOtpVerificationAwaitingCode
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
        enableButton = isCodeFilled,
        text = "Enviar"
    )
    Spacer(modifier = Modifier.padding(8.dp))
    Text(
        text = "Dica: Caso não encontre o e-mail na sua caixa de entrada, verifique a pasta de Spam!",
        style = MaterialTheme.typography.titleLarge,
        textAlign = TextAlign.Center,
        color = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.primary else Color.Unspecified
    )
}