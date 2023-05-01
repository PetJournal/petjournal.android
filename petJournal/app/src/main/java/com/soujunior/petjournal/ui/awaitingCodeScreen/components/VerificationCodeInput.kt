package com.soujunior.petjournal.ui.awaitingCodeScreen.components

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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.soujunior.petjournal.ui.theme.FredokaRegular
import com.soujunior.petjournal.ui.theme.FredokaSemiBold

@Composable
fun VerificationCodeInput() {
    val resendCodeStyle = TextStyle(
        fontFamily = FontFamily(FredokaRegular),
        fontSize = 14.sp,
        textDecoration = TextDecoration.Underline,
    )
    var otpValue by remember { mutableStateOf("") }
    OTPTextField(
        otpText = otpValue,
        onOtpTextChange = { value, otpInputFilled -> otpValue = value })

    Box(
        modifier = Modifier
            .padding(start = 4.dp, top = 2.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = "Reenviar código?",
            style = resendCodeStyle
            //textAlign = TextAlign.Start,
            //modifier = Modifier.align(Alignment.CenterStart)
        )

    }
}


/* efeito sublinhado precisa de 2 boxes
Box(
        modifier = Modifier
            .padding(start = 4.dp, top = 2.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.CenterStart
    ) {
        Box(
            modifier = Modifier
                .width(IntrinsicSize.Max),
            //contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = "Reenviar código?",
                style = resendCodeStyle,
                //textAlign = TextAlign.Start,
                //modifier = Modifier.align(Alignment.CenterStart)
            )
            Divider(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(top = 8.dp)
                    .fillMaxWidth()
                    .height(2.dp)
                    .background(Color.Black)
            )
        }

    }

 */