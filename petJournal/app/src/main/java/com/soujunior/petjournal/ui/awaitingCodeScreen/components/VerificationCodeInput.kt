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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun VerificationCodeInput() {
    var otpValue by remember { mutableStateOf("") }
    OTPTextField(
        otpText = otpValue,
        onOtpTextChange = { value, otpInputFilled -> otpValue = value })
    Text(
        text = "Reenviar código?",
        modifier = Modifier
            .padding(start = 12.dp, top = 12.dp)
    )
}