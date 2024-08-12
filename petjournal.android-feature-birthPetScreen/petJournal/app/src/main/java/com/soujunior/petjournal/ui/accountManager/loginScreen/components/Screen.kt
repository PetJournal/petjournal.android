package com.soujunior.petjournal.ui.accountManager.loginScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.accountManager.loginScreen.LoginFormEvent
import com.soujunior.petjournal.ui.accountManager.loginScreen.LoginViewModel
import com.soujunior.petjournal.ui.components.InputText

@Composable
fun Screen(navController: NavController, viewModel: LoginViewModel) {

    val systemUiController = rememberSystemUiController()

    systemUiController.setSystemBarsColor(
        color = Color.Transparent,
        darkIcons = true
    )
    systemUiController.setNavigationBarColor(Color.Black)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp)
                .systemBarsPadding()
                .align(Alignment.TopCenter)
        ) {
            Spacer(modifier = Modifier.weight(0.2f))
            LoginHeader()
            Spacer(modifier = Modifier.weight(0.1f))
            InputText(
                textTop = stringResource(id = R.string.email_label),
                textHint = stringResource(id = R.string.email_hint),
                textValue = viewModel.state.email,
                textError = viewModel.state.emailError,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .testTag("input_email"),
                onEvent = { it: String -> viewModel.onEvent(LoginFormEvent.EmailChanged(it)) }
            )
            InputText(
                textTop = stringResource(id = R.string.password_label),
                isPassword = true,
                textHint = stringResource(id = R.string.password_hint),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("input_password"),
                textValue = viewModel.state.password,
                textError = viewModel.state.passwordError,
                onEvent = { it: String -> viewModel.onEvent(LoginFormEvent.PasswordChanged(it)) }
            )
            RememberPasswordAndForgotSection(navController, viewModel)
            Spacer(modifier = Modifier.weight(0.1f))
            Footer(navController, viewModel)
            Spacer(modifier = Modifier.weight(0.5f))
        }
    }
}
