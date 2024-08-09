package com.soujunior.petjournal.ui.screens_app.account_manager.forgotPasswordScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.components.DashedInputText
import com.soujunior.petjournal.ui.components.HeaderImageLogoImagePasswordAndTitle
import com.soujunior.petjournal.ui.screens_app.account_manager.forgotPasswordScreen.ForgotPasswordFormEvent
import com.soujunior.petjournal.ui.screens_app.account_manager.forgotPasswordScreen.ForgotPasswordViewModel
import ir.kaaveh.sdpcompose.sdp

@Composable
fun Screen(navController: NavController, viewModel: ForgotPasswordViewModel) {
    val systemUiController = rememberSystemUiController()

    systemUiController.setSystemBarsColor(color = Color.Transparent, darkIcons = true)
    systemUiController.setNavigationBarColor(Color.Black)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ) {
            HeaderImageLogoImagePasswordAndTitle(
                subText = stringResource(R.string.reset_password_in_two_steps),
                title = stringResource(R.string.forgot_password),
                spaceBetween = 40.sdp,
                styleTitle = MaterialTheme.typography.headlineLarge
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.sdp, end = 20.sdp, top = 40.sdp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center,
            ) {
                item {
                    DashedInputText(
                        titleText = stringResource(R.string.what_is_your_registered_email_address),
                        placeholderText = stringResource(R.string.email_hint),
                        textValue = viewModel.state.email,
                        textError = viewModel.state.emailError,
                        isError = !viewModel.state.emailError.isNullOrEmpty(),
                        onEvent = { it: String ->
                            viewModel.onEvent(
                                ForgotPasswordFormEvent.EmailChanged(it)
                            )
                        }
                    )
                }
                item {
                    Footer(navController, viewModel)
                }
            }
        }
    }
}