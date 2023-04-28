package com.soujunior.petjournal.ui.awaitingCodeScreen.components

import androidx.compose.foundation.layout.*
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
    var isCodeFilled = false

    /*if (textfield do otp.length == 6) {
        isCodeFilled = true
    }*/

    Column(
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 16.dp)
    ) {


        ButtonSend(isCodeFilled = isCodeFilled, awaitingCodeScreenViewModel)

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Dica: Caso não encontre o e-mail na sua caixa de entrada, verifique a pasta de Spam!",
            //style = MaterialTheme.typography.body2,
            textAlign = TextAlign.Center
        )
    }
}