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


