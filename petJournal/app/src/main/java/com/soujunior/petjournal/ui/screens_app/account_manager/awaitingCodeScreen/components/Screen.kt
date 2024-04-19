package com.soujunior.petjournal.ui.screens_app.account_manager.awaitingCodeScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.soujunior.petjournal.ui.components.Button2
import com.soujunior.petjournal.ui.components.CreateTitleAndImageLogo
import com.soujunior.petjournal.ui.screens_app.account_manager.awaitingCodeScreen.AwaitingCodeViewModel
import com.soujunior.petjournal.ui.states.TaskState
import com.soujunior.petjournal.ui.theme.FredokaRegular

@Composable
fun Screen(navController: NavController, viewModel: AwaitingCodeViewModel) {
    val buttonIsEnable by viewModel.buttonIsEnable.collectAsState()
    val taskState by viewModel.taskState.collectAsState()
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(color = Color.Transparent, darkIcons = true)
    systemUiController.setNavigationBarColor(Color.Black)
    val state by viewModel.state.collectAsState()
    val resendCodeStyle = TextStyle(
        fontFamily = FontFamily(FredokaRegular),
        fontSize = 14.sp,
        textDecoration = TextDecoration.Underline,
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .navigationBarsPadding()
                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                .padding(horizontal = 16.dp)
                .padding(top = 32.dp),
            content = {
                Spacer(modifier = Modifier.weight(0.1f))
                CreateTitleAndImageLogo(
                    title = "Acabamos de enviar um código para seu e-mail",
                    spaceBetween = 16.dp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.weight(0.1f))
                //TODO("Extrair string")

                Text(
                    text = "Insira no campo abaixo o código de verificação de 6 digitos enviado para o seu email.",
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    color = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.primary else Color.Unspecified
                )
                Spacer(modifier = Modifier.weight(0.1f))

                OTPTextField(
                    textValue = state.codeOTP,
                    onEvent = { code: String ->
                        viewModel.onEvent(
                            com.soujunior.petjournal.ui.screens_app.account_manager.awaitingCodeScreen.AwaitingCodeFormEvent.CodeOTPChanged(
                                code
                            )
                        )
                    },
                    textError = state.codeOTPError
                )
                Box(
                    modifier = Modifier
                        .padding(top = 10.dp, end = 14.5.dp)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Text(
                        text = "Reenviar código?",
                        style = resendCodeStyle,
                        color = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.primary else Color.Unspecified,
                        modifier = Modifier.clickable {
                            viewModel.onEvent(com.soujunior.petjournal.ui.screens_app.account_manager.awaitingCodeScreen.AwaitingCodeFormEvent.ResendCode)
                        }
                    )
                }

                Spacer(modifier = Modifier.weight(0.1f))
                Button2(
                    text = "Enviar",
                    border = null,
                    submit = { viewModel.onEvent(com.soujunior.petjournal.ui.screens_app.account_manager.awaitingCodeScreen.AwaitingCodeFormEvent.Submit) },
                    enableButton = buttonIsEnable,
                    modifier = Modifier.size(height = 50.dp, width = 240.dp),
                    buttonColor = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    isLoading = taskState is TaskState.Loading
                )
                Spacer(modifier = Modifier.weight(0.1f))
                Text(
                    text = "Dica: Caso não encontre o e-mail na sua caixa de entrada, verifique a pasta de Spam!",
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    color = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.primary else Color.Unspecified
                )
                Spacer(modifier = Modifier.weight(0.1f))

            }
        )
    }
}