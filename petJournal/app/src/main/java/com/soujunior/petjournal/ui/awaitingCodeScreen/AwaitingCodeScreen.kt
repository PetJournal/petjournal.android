package com.soujunior.petjournal.ui.awaitingCodeScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.soujunior.petjournal.ui.awaitingCodeScreen.components.ButtonSend
import com.soujunior.petjournal.ui.loginScreen.AwaitingCodeScreenViewModel
import com.soujunior.petjournal.ui.loginScreen.components.ButtonLogin
import com.soujunior.petjournal.ui.registerScreen.components.ImageLogo
import com.soujunior.petjournal.ui.util.isEmail
import org.koin.androidx.compose.getViewModel


@Composable
fun AwaitingCodeScreen() {

    Header()
    VerificationCodeInput()
    Footer()

}

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

@Composable
fun VerificationCodeInput() {
    TODO("Not yet implemented")
}

@Composable
fun Footer() {
    val awaitingCodeScreenViewModel: AwaitingCodeScreenViewModel = getViewModel()
    var isCodeFilled = false

    /*if (textfield do otp.length == 6) {
        isCodeFilled = true
    }*/

    Column(
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 16.dp)
    ) {


        ButtonSend(isCodeFilled = isCodeFilled, awaitingCodeScreenViewModel)

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Dica: Caso não encontre o e-mail na sua caixa de entrada, verifique a pasta de Spam!",
            //style = MaterialTheme.typography.body2,
            textAlign = TextAlign.Center
        )
    }
}
