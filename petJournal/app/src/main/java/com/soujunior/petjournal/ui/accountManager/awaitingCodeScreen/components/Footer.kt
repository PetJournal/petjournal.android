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

@Composable
fun Footer(navController: NavController , viewModel: AwaitingCodeViewModel) {
    //val email by States.localEmailState.current // Redefinir para pegar o Email a partir do novo States
    Spacer(modifier = Modifier.padding(20.dp))

    Button(
        submit = {
            viewModel.onEvent(AwaitingCodeFormEvent.Submit)
        },
        enableButton = viewModel.enableButton(),
        border = null,
        text = "Enviar",
        inDarkMode = true,
        setSystemBarColor = false
    )
    Spacer(modifier = Modifier.padding(5.dp))
    Text(
        text = "Dica: Caso não encontre o e-mail na sua caixa de entrada, verifique a pasta de Spam!",
        style = MaterialTheme.typography.titleLarge,
        textAlign = TextAlign.Center,
        color = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.primary else Color.Unspecified
    )
}