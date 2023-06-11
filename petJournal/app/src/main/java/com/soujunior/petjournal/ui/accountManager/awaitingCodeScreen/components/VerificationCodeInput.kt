package com.soujunior.petjournal.ui.accountManager.awaitingCodeScreen.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.soujunior.petjournal.ui.accountManager.awaitingCodeScreen.AwaitingCodeFormEvent
import com.soujunior.petjournal.ui.accountManager.awaitingCodeScreen.AwaitingCodeViewModel
import com.soujunior.petjournal.ui.theme.FredokaRegular

//Todo: (Leo) parametro nao esta sendo usado
@Composable
fun VerificationCodeInput(viewModel: AwaitingCodeViewModel) {
    val resendCodeStyle = TextStyle(
        fontFamily = FontFamily(FredokaRegular),
        fontSize = 14.sp,
        textDecoration = TextDecoration.Underline,
    )
    /*LaunchedEffect(Unit) {
        if (otpText.length > otpCount) {
            throw IllegalArgumentException("O valor do texto OTP não deve ter mais de $otpCount caracteres")
        }
    }*/

    OTPTextField(
        textValue = viewModel.state.codeOTP,
        onEvent = { it: String ->
            viewModel.onEvent(
                AwaitingCodeFormEvent.CodeOTPChanged(
                    it
                )
            )
        },
        textError = viewModel.state.codeOTPError
    )
    Box(
        modifier = Modifier
            .padding(start = 15.5.dp, top = 2.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = "Reenviar código?",
            style = resendCodeStyle,
            color = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.primary else Color.Unspecified
        )
    }
}