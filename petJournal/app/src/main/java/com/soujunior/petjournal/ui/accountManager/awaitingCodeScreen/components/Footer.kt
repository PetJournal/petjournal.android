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
import androidx.navigation.NavController
import com.soujunior.petjournal.ui.accountManager.awaitingCodeScreen.AwaitingCodeFormEvent
import com.soujunior.petjournal.ui.accountManager.awaitingCodeScreen.AwaitingCodeViewModel
import com.soujunior.petjournal.ui.components.Button
import com.soujunior.petjournal.ui.states.States
import org.koin.androidx.compose.getViewModel

//TODO: (Leo) parametro nao esta sendo usado
//TODO: (Leo) Criar lógica para habilitar botao somente quando o campo inteiro for preenchido
@Composable
fun Footer(navController: NavController , viewModel: AwaitingCodeViewModel) {
    val awaitingCodeViewModel: AwaitingCodeViewModel = getViewModel()
    var isCodeFilled = false
    val email by States.localEmailState.current
    val otpFullCode by States.otpFullCode.current
    Spacer(modifier = Modifier.padding(20.dp))

    if (otpFullCode.isNotEmpty() && otpFullCode.isNotBlank() && otpFullCode.length == 6) {
        isCodeFilled = true
    }
    Button(
        submit = {
            viewModel.onEvent(AwaitingCodeFormEvent.Submit)
        },
        enableButton = isCodeFilled,
        border = null,
        text = "Enviar",
        setSystemBarColor = true,
        inDarkMode = true
    )
    Spacer(modifier = Modifier.padding(5.dp))
    Text(
        text = "Dica: Caso não encontre o e-mail na sua caixa de entrada, verifique a pasta de Spam!",
        style = MaterialTheme.typography.titleLarge,
        textAlign = TextAlign.Center,
        color = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.primary else Color.Unspecified
    )
}