package com.soujunior.petjournal.ui.screens_app.account_manager.forgotPasswordScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.soujunior.petjournal.ui.components.CreateTitleAndImageLogo
import com.soujunior.petjournal.ui.components.InputText
import com.soujunior.petjournal.ui.screens_app.account_manager.forgotPasswordScreen.ForgotPasswordFormEvent
import com.soujunior.petjournal.ui.screens_app.account_manager.forgotPasswordScreen.ForgotPasswordViewModel

@Composable
fun Screen(navController: NavController, viewModel: ForgotPasswordViewModel) {
    val systemUiController = rememberSystemUiController()
    val isDarkMode = isSystemInDarkTheme()

    systemUiController.setSystemBarsColor(color = Color.Transparent, darkIcons = true)
    systemUiController.setNavigationBarColor(Color.Black)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .systemBarsPadding()
                .padding(start = 20.dp, end = 20.dp)
                .align(Alignment.TopCenter)
        ) {
            Spacer(modifier = Modifier.weight(0.1f))
            CreateTitleAndImageLogo(
                title = "Esqueceu a senha?",
                styleTitle = MaterialTheme.typography.displayMedium,
                modifierImage = Modifier
                    .size(width = 200.dp, height = 200.dp)
                    .padding(top = 20.dp),
            )
            Spacer(modifier = Modifier.weight(0.1f))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = "Redefina a sua senha em duas etapas",
                    style = MaterialTheme.typography.titleLarge,
                    color = if (isDarkMode) MaterialTheme.colorScheme.primary else Color.Unspecified,
                )
            }
            Spacer(modifier = Modifier.weight(0.1f))
            //TODO("Extrair string")

            Row(modifier = Modifier.fillMaxWidth()) {
                InputText(
                    textTop = "Qual seu e-mail de cadastro?",
                    textHint = "eg: exemple@petjournal.com",
                    textValue = viewModel.state.email,
                    textError = viewModel.state.emailError,
                    onEvent = { it: String ->
                        viewModel.onEvent(
                            ForgotPasswordFormEvent.EmailChanged(
                                it
                            )
                        )
                    }
                )
            }
            Spacer(modifier = Modifier.weight(0.2f))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
            ) {
                Footer(navController, viewModel)
            }
            Spacer(modifier = Modifier.weight(0.3f))
        }
    }
}