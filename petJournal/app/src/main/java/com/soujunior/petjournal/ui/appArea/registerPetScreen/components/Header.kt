package com.soujunior.petjournal.ui.appArea.registerPetScreen.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun Header() {
    //Spacer(modifier = Modifier.padding(16.dp))

    Text(
        text = "Ola (1 nome do usuário)!",
        style = MaterialTheme.typography.titleSmall,
        textAlign = TextAlign.Center,
        color = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.onBackground  else MaterialTheme.colorScheme.primary
    )

    Spacer(modifier = Modifier.padding(12.dp))

    Text(
        text = "Sabemos o quanto o seu pet é especial, e estamos muito animados em recebê-los, venha se juntar a nossa comunidade de amantes de Pets, para melhor aproveitar a nossa plataforma.",
        style = MaterialTheme.typography.titleLarge,
        textAlign = TextAlign.Center,
        color = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.primary
    )
    Spacer(modifier = Modifier.padding(top = 32.dp))


}