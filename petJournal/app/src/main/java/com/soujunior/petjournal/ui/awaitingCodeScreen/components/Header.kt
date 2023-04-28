package com.soujunior.petjournal.ui.awaitingCodeScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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

    val textStyleCodeBold = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
    )
    val textStyleCodeNormal = TextStyle(
        fontSize = 15.sp,
        fontWeight = FontWeight.Normal,
        textAlign = TextAlign.Center,
    )


    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.padding(top = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            ImageLogo(modifier = Modifier.size(200.dp))
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Acabamos de enviar um código para seu e-mail",
            style = textStyleCodeBold,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Insira no campo abaixo o código de verificação de 6 digitos enviado para o seu email.",
            style = textStyleCodeNormal,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(64.dp))

    }
}