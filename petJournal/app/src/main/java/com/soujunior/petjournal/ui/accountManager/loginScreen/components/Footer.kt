package com.soujunior.petjournal.ui.accountManager.loginScreen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.soujunior.petjournal.ui.accountManager.loginScreen.LoginFormEvent
import com.soujunior.petjournal.ui.accountManager.loginScreen.LoginViewModel
import com.soujunior.petjournal.ui.components.Button

@Composable
fun Footer(
    navController: NavController,
    viewModel: LoginViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row {
            Button(
                text = "Continuar",
                submit = {
                    viewModel.onEvent(LoginFormEvent.Submit)
                },
                enableButton = true,
                modifier = Modifier.size(height = 50.dp, width = 240.dp)
            )
        }
        Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text(
                text = "Não tem uma conta? Inscreva-se",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .clickable(onClick = { navController.navigate("register") })
                    .padding(top = 20.dp),
                color = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.primary else Color.Unspecified
            )
        }
    }
}