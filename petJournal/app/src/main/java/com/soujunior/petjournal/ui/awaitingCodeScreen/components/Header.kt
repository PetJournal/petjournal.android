package com.soujunior.petjournal.ui.awaitingCodeScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.soujunior.petjournal.ui.registerScreen.components.ImageLogo

@Composable
fun Header() {
        ImageLogo(modifier = Modifier.size(200.dp))

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Acabamos de enviar um código para seu e-mail",
            style = MaterialTheme.typography.h2,
            textAlign = TextAlign.Center
            //modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Insira no campo abaixo o código de verificação de 6 digitos enviado para o seu email.",
            style = MaterialTheme.typography.subtitle1,
            textAlign = TextAlign.Center
            //modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(64.dp))


}