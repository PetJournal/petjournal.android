package com.soujunior.petjournal.ui.accountManager.awaitingCodeScreen.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.soujunior.petjournal.ui.components.CreateTitleAndImageLogo
import com.soujunior.petjournal.ui.components.ImageLogo

@Composable
fun Header() {
    CreateTitleAndImageLogo(
        title = "Acabamos de enviar um código para seu e-mail",
        spaceBetween = 16.dp,
        textAlign = TextAlign.Center

    )

    Spacer(modifier = Modifier.padding(16.dp))

    Text(
        text = "Insira no campo abaixo o código de verificação de 6 digitos enviado para o seu email.",
        style = MaterialTheme.typography.titleLarge,
        textAlign = TextAlign.Center,
        color = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.primary else Color.Unspecified
    )

    Spacer(modifier = Modifier.padding(32.dp))


}