package com.soujunior.petjournal.ui.awaitingCodeScreen.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.soujunior.petjournal.ui.components.ImageLogo

@Composable
fun Header() {
    ImageLogo(modifier = Modifier.size(200.dp))
    Spacer(modifier = Modifier.padding(16.dp))

    Text(
        text = "Acabamos de enviar um código para seu e-mail",
        style = MaterialTheme.typography.h2,
        textAlign = TextAlign.Center
    )

    Spacer(modifier = Modifier.padding(16.dp))

    Text(
        text = "Insira no campo abaixo o código de verificação de 6 digitos enviado para o seu email.",
        style = MaterialTheme.typography.subtitle1,
        textAlign = TextAlign.Center
    )

    Spacer(modifier = Modifier.padding(32.dp))


}