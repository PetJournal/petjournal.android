package com.soujunior.petjournal.ui.screens_app.account_manager.awaitingCodeScreen.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.soujunior.petjournal.ui.components.Button2
import com.soujunior.petjournal.ui.screens_app.account_manager.awaitingCodeScreen.AwaitingCodeViewModel
import com.soujunior.petjournal.ui.states.TaskState

@Composable
fun Footer(navController: NavController, viewModel: AwaitingCodeViewModel) {
    val buttonIsEnable by viewModel.buttonIsEnable.collectAsState()
    val taskState by viewModel.taskState.collectAsState()

    Spacer(modifier = Modifier.padding(20.dp))
    Button2(
        text = "Enviar",
        border = null,
        submit = { viewModel.onEvent(com.soujunior.petjournal.ui.screens_app.account_manager.awaitingCodeScreen.AwaitingCodeFormEvent.Submit) },
        enableButton = buttonIsEnable,
        modifier = Modifier.size(height = 50.dp, width = 240.dp),
        buttonColor = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        isLoading = taskState is TaskState.Loading
    )
    Spacer(modifier = Modifier.padding(5.dp))
    //TODO("Extrair string")
    Text(
        text = "Dica: Caso não encontre o e-mail na sua caixa de entrada, verifique a pasta de Spam!",
        style = MaterialTheme.typography.titleLarge,
        textAlign = TextAlign.Center,
        color = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.primary else Color.Unspecified
    )
}