package com.soujunior.petjournal.ui.accountManager.loginScreen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.accountManager.loginScreen.LoginFormEvent
import com.soujunior.petjournal.ui.accountManager.loginScreen.LoginViewModel
import com.soujunior.petjournal.ui.components.CheckboxWithText
import com.soujunior.petjournal.ui.components.CreateTitleAndImageLogo
import com.soujunior.petjournal.ui.components.InputText

@Composable
fun Screen(navController: NavController, viewModel: LoginViewModel) {
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 20.dp, end = 20.dp)
                .align(Alignment.TopCenter)
        ) {
            item { LoginHeader() }
            item {
                InputText(
                    textTop = stringResource(id = R.string.email_label),
                    textHint = stringResource(id = R.string.email_hint),
                    textValue = viewModel.state.email,
                    textError = viewModel.state.emailError,
                    onEvent = { it: String -> viewModel.onEvent(LoginFormEvent.EmailChanged(it)) }
                )
            }
            item { Spacer(modifier = Modifier.padding(top = 30.dp)) }
            item {
                InputText(
                    textTop = stringResource(id = R.string.password_label),
                    isPassword = true,
                    textHint = stringResource(id = R.string.password_hint),
                    textValue = viewModel.state.password,
                    textError = viewModel.state.passwordError,
                    onEvent = { it: String -> viewModel.onEvent(LoginFormEvent.PasswordChanged(it)) }
                )
            }
            item { RememberPasswordAndForgotSection(navController, viewModel) }
            item { Spacer(modifier = Modifier.padding(top = 70.dp)) }
            item { Footer(navController, viewModel) }
        }
    }
}
