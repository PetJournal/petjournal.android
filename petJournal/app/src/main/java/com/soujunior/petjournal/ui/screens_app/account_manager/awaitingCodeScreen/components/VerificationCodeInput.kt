package com.soujunior.petjournal.ui.screens_app.account_manager.awaitingCodeScreen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.soujunior.petjournal.ui.screens_app.account_manager.awaitingCodeScreen.AwaitingCodeViewModel
import com.soujunior.petjournal.ui.theme.FredokaRegular

@Composable
fun VerificationCodeInput(viewModel: AwaitingCodeViewModel) {
    val state by viewModel.state.collectAsState()
    val resendCodeStyle = TextStyle(
        fontFamily = FontFamily(FredokaRegular),
        fontSize = 14.sp,
        textDecoration = TextDecoration.Underline,
    )

    OTPTextField(
        textValue = state.codeOTP,
        onEvent = { code: String ->
            viewModel.onEvent(
                com.soujunior.petjournal.ui.screens_app.account_manager.awaitingCodeScreen.AwaitingCodeFormEvent.CodeOTPChanged(
                    code
                )
            )
        },
        textError = state.codeOTPError,
        viewModel = viewModel
    )
    Box(
        modifier = Modifier
            .padding(top = 6.dp, end = 14.5.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.CenterEnd
    ) {
        //TODO("Extrair string")
        Text(
            text = "Reenviar código?",
            style = resendCodeStyle,
            color = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.primary else Color.Unspecified,
            modifier = Modifier.clickable {
                viewModel.onEvent(com.soujunior.petjournal.ui.screens_app.account_manager.awaitingCodeScreen.AwaitingCodeFormEvent.ResendCode)
            }
        )
    }
}