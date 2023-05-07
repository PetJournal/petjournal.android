package com.soujunior.petjournal.ui.awaitingCodeScreen.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.soujunior.petjournal.ui.states.States
import com.soujunior.petjournal.ui.theme.FredokaRegular

@Composable
fun VerificationCodeInput() {
    val resendCodeStyle = TextStyle(
        fontFamily = FontFamily(FredokaRegular),
        fontSize = 14.sp,
        textDecoration = TextDecoration.Underline,
    )
    var otpValue by States.otpFullCode.current

    OTPTextField(
        otpText = otpValue,
        onOtpTextChange = { value, otpInputFilled -> otpValue = value })

    Box(
        modifier = Modifier
            .padding(start = 15.5.dp, top = 2.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = "Reenviar código?",
            style = resendCodeStyle
        )
    }
}