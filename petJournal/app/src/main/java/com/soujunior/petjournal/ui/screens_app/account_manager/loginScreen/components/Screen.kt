package com.soujunior.petjournal.ui.screens_app.account_manager.loginScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.components.DashedInputText
import com.soujunior.petjournal.ui.screens_app.account_manager.loginScreen.LoginFormEvent
import com.soujunior.petjournal.ui.screens_app.account_manager.loginScreen.LoginViewModel
import ir.kaaveh.sdpcompose.sdp

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
            .background(MaterialTheme.colorScheme.background),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ) {
            LoginHeader()

        }
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ) {

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 20.sdp, end = 20.sdp, top = 170.sdp, bottom = 60.sdp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top,
            ) {
                item {
                    DashedInputText(
                        modifier = Modifier.testTag("input_email"),
                        textInputModifier = Modifier.fillMaxWidth(),
                        placeholderText = stringResource(id = R.string.email_hint),
                        textValue = viewModel.state.email,
                        textError = viewModel.state.emailError,
                        isError = !viewModel.state.emailError.isNullOrEmpty(),
                        titleText = stringResource(id = R.string.email_label),
                        onEvent = { it: String ->
                            viewModel.onEvent(LoginFormEvent.EmailChanged(it))
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email)
                    )
                }
                item {
                    DashedInputText(
                        titleText = stringResource(id = R.string.password_label),
                        textInputModifier = Modifier.fillMaxWidth(),
                        isPassword = true,
                        placeholderText = stringResource(id = R.string.password_hint),
                        modifier = Modifier.testTag("input_password"),
                        textValue = viewModel.state.password,
                        textError = viewModel.state.passwordError,
                        isError = !viewModel.state.passwordError.isNullOrEmpty(),
                        onEvent = { it: String ->
                            viewModel.onEvent(LoginFormEvent.PasswordChanged(it))
                        }
                    )
                }
                item {
                    RememberPasswordAndForgotSection(navController, viewModel)
                }
                item {

                    Spacer(modifier = Modifier.padding(top = 75.sdp))

                }
                item {

                    Footer(navController, viewModel)

                }
            }
        }
    }
}
