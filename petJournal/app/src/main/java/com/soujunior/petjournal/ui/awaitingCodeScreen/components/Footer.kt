package com.soujunior.petjournal.ui.awaitingCodeScreen.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.soujunior.petjournal.ui.awaitingCodeScreen.AwaitingCodeScreenViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun Footer() {
    val awaitingCodeScreenViewModel: AwaitingCodeScreenViewModel = getViewModel()
    var isCodeFilled = true

    /*if (textfield do otp.length == 6) {
        isCodeFilled = true
    }*/

    Spacer(modifier = Modifier.padding(32.dp))

    ButtonSend(isCodeFilled = isCodeFilled, awaitingCodeScreenViewModel)

    Spacer(modifier = Modifier.padding(8.dp))

    Text(
        text = "Dica: Caso não encontre o e-mail na sua caixa de entrada, verifique a pasta de Spam!",
        style = MaterialTheme.typography.subtitle1,
        textAlign = TextAlign.Center
    )

}