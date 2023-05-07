package com.soujunior.petjournal.ui.awaitingCodeScreen.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.soujunior.domain.entities.auth.LoginModel
import com.soujunior.petjournal.ui.awaitingCodeScreen.AwaitingCodeScreenViewModel
import com.soujunior.petjournal.ui.loginScreen.LoginScreenViewModel
import com.soujunior.petjournal.ui.loginScreen.postFormLogin
import com.soujunior.petjournal.ui.states.States
import com.soujunior.petjournal.ui.theme.Shapes

@Composable
fun ButtonSend(sendCode: () -> Unit, isCodeFilled: Boolean) {
    //Declara OTP value aqui
    Button(
        //Função para mandar código OTP
        onClick = { sendCode() },
        enabled = isCodeFilled,
        colors = ButtonDefaults.buttonColors(

        ),
        modifier = Modifier
            .clip(RoundedCornerShape(30.dp, 30.dp, 30.dp, 30.dp))
            .fillMaxWidth(0.6f)
            .height(60.dp),
        shape = Shapes.large
    ) {
        Text(
            text = "Enviar",
            style = MaterialTheme.typography.button,
        )
    }
}