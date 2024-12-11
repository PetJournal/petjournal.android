package com.soujunior.petjournal.ui.screens_app.account_manager.awaitingCodeScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.screens_app.account_manager.awaitingCodeScreen.AwaitingCodeFormEvent
import com.soujunior.petjournal.ui.screens_app.account_manager.awaitingCodeScreen.AwaitingCodeViewModel
import ir.kaaveh.sdpcompose.sdp

@Composable
fun Screen(navController: NavController, viewModel: AwaitingCodeViewModel) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(color = Color.Transparent, darkIcons = true)
    systemUiController.setNavigationBarColor(Color.Black)
    val state by viewModel.state.collectAsState()


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        )
        {
            Header()

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.sdp, end = 20.sdp, top = 20.sdp, bottom = 40.sdp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                item {
                    OTPTextField(
                        textValue = state.codeOTP,
                        isError = !state.codeOTPError.isNullOrEmpty(),
                        onEvent = { code: String ->
                            viewModel.onEvent(
                                AwaitingCodeFormEvent.CodeOTPChanged(
                                    code
                                )
                            )
                        },
                        textError = state.codeOTPError,
                        viewModel = viewModel
                    )
                }
                item {
                    Box(
                        modifier = Modifier
                            .padding(start = 10.sdp, end = 10.sdp)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.TopStart
                    ){

                        Text(
                            text = stringResource(R.string.txt_tip_If_you_dont_find_the_email_in_your_inbox_check_your_spam_folder),
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Start,
                            color = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(20.sdp))
                    Footer(navController = navController, viewModel = viewModel)

                }
            }
        }
    }
}