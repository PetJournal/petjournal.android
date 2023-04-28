package com.soujunior.petjournal.ui.awaitingCodeScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
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
    var otpValue by remember { mutableStateOf("") }
    val borderS = 2.5

    BasicTextField(
        value = otpValue,
        onValueChange = {
            if (it.length <= 6) {
                otpValue = it
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.NumberPassword
        ),
        decorationBox = {

            Row(horizontalArrangement = Arrangement.Center) {

                repeat(6) {
                    val char = when {
                        it >= otpValue.length -> ""
                        else -> otpValue[it].toString()
                    }
                    val isFocused = otpValue.length == it
                    Text(
                        modifier = Modifier
                            .width(56.dp)
                            .height(70.dp)
                            .background(
                                when {
                                    char.isNotEmpty() -> Color.White
                                    else -> Color.LightGray
                                }, RoundedCornerShape(8.dp)
                            )
                            .border(
                                borderS.dp,
                                if (isFocused) {
                                    Color.Black
                                } else {
                                    when {
                                        char.isEmpty() -> Color(0xFBAFD9DB)
                                        else -> Color(0xFB9A0963)
                                    }
                                }, RoundedCornerShape(8.dp)
                            )
                            .padding(12.dp, 12.dp),
                        text = char,
                        style = MaterialTheme.typography.h4,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )
                    if (it < 5) {
                        Spacer(modifier = Modifier.width(12.dp))
                    }

                }

            }
        }
    )
    Text(
        text = "Reenviar código?",
        modifier = Modifier
            .padding(start = 12.dp, top = 12.dp)
    )
}

/*@Preview
@Composable
fun VerificationCodeInput() {
    val otp = remember { mutableStateOf(List(6) { "" }) }

    Row {
        for (i in 0 until 6) {
            TextField(
                value = otp.value[i],
                onValueChange = { newValue ->
                    if (newValue.length <= 1) {
                        otp.value = otp.value.toMutableList().also { it[i] = newValue }
                    }
                },
                modifier = Modifier.width(40.dp),
                singleLine = true,
                maxLines = 1,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                textStyle = LocalTextStyle.current.copy(
                    fontSize = 18.sp
                )
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}*/

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
