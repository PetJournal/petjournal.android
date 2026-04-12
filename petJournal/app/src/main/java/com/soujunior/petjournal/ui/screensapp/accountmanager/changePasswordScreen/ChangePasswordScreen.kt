package com.soujunior.petjournal.ui.screensapp.accountmanager.changePasswordScreen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.components.Button2
import com.soujunior.petjournal.ui.components.Button3
import com.soujunior.petjournal.ui.components.DashedInputText
import com.soujunior.petjournal.ui.components.HeaderImageLogoImagePasswordAndTitle
import com.soujunior.petjournal.ui.screensapp.accountmanager.changePasswordScreen.components.LogoutDevicesChangingPassword
import com.soujunior.petjournal.ui.states.TaskState
import com.soujunior.petjournal.ui.theme.PetJournalTheme
import com.soujunior.petjournal.ui.util.ValidationEvent
import ir.kaaveh.sdpcompose.sdp
import org.koin.androidx.compose.getViewModel

@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun getChangePasswordViewModelForPreview(): ChangePasswordViewModel {
    return if (LocalInspectionMode.current) {
        FakeChangePasswordViewModel()
    } else {
        getViewModel()
    }
}

@Composable
fun ChangePasswordScreen(navController: NavController) {
    val viewModel: ChangePasswordViewModel = getChangePasswordViewModelForPreview()
    val context = LocalContext.current
    LaunchedEffect(key1 = context) {
        viewModel.validationEvents.collect { event ->
            when (event) {
                is ValidationEvent.Success -> {
                    Toast.makeText(context, R.string.success, Toast.LENGTH_LONG).show()
                    navController.popBackStack("forgotPassword", inclusive = true)
                }

                is ValidationEvent.Failed -> {
                    Toast.makeText(context, viewModel.message.value, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    val taskState by viewModel.taskState.collectAsState()
    val systemUiController = rememberSystemUiController()
    val isDarkMode = isSystemInDarkTheme()
    systemUiController.setSystemBarsColor(color = Color.Transparent, darkIcons = true)
    systemUiController.setNavigationBarColor(Color.Black)

    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .navigationBarsPadding()
                .statusBarsPadding()
                .background(MaterialTheme.colorScheme.background),
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .navigationBarsPadding()
                    .statusBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ) {
            HeaderImageLogoImagePasswordAndTitle(
                title = stringResource(R.string.now_create_a_new_password),
                spaceBetween = 10.sdp,
                styleTitle = MaterialTheme.typography.headlineLarge,
            )
            LazyColumn(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(start = 20.sdp, end = 20.sdp, bottom = 40.sdp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center,
            ) {
                item {
                    DashedInputText(
                        titleText = stringResource(R.string.enter_a_new_password),
                        placeholderText = stringResource(id = R.string.password_hint),
                        textValue = viewModel.state.password,
                        textError = viewModel.state.passwordError,
                        isError = !viewModel.state.passwordError.isNullOrEmpty(),
                        isPassword = true,
                        onEvent = { it: String ->
                            viewModel.onEvent(
                                ChangePasswordFormEvent.PasswordChanged(
                                    it,
                                ),
                            )
                        },
                    )
                }
                item {
                    DashedInputText(
                        titleText = stringResource(id = R.string.confirm_password_label),
                        placeholderText = stringResource(R.string.enter_your_password_again),
                        textValue = viewModel.state.repeatedPassword,
                        textError = viewModel.state.repeatedPasswordError,
                        isPassword = true,
                        onEvent = { it: String ->
                            viewModel.onEvent(
                                ChangePasswordFormEvent.ConfirmPasswordChanged(
                                    it,
                                ),
                            )
                        },
                    )
                }

                item {
                    LogoutDevicesChangingPassword(
                        valueChecked = viewModel.state.disconnectOtherDevices,
                        onEvent = { it: Boolean ->
                            viewModel.onEvent(
                                ChangePasswordFormEvent.DisconnectOtherDevices(
                                    it,
                                ),
                            )
                        },
                    )
                }
                item {
                    Row(
                        Modifier.padding(top = 40.sdp, bottom = 40.sdp),
                    ) {
                        Button3(
                            submit = { navController.popBackStack() },
                            enableButton = true,
                            modifier =
                                Modifier
                                    .weight(1f)
                                    .padding(end = 4.sdp),
                            text = stringResource(R.string.back),
                            buttonColor =
                                ButtonDefaults.buttonColors(
                                    MaterialTheme.colorScheme.surface,
                                ),
                            textColor = MaterialTheme.colorScheme.primary,
                        )
                        Button2(
                            modifier =
                                Modifier
                                    .weight(1f)
                                    .padding(start = 4.sdp),
                            border = null,
                            buttonColor =
                                if (isDarkMode) {
                                    ButtonDefaults.buttonColors(MaterialTheme.colorScheme.onPrimary)
                                } else {
                                    ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
                                },
                            textColor = if (isDarkMode) MaterialTheme.colorScheme.primary else Color.White,
                            submit = {
                                viewModel.onEvent(event = ChangePasswordFormEvent.Submit)
                            },
                            enableButton = viewModel.enableButton(),
                            text = stringResource(id = R.string.reset_password_button),
                            isLoading = taskState is TaskState.Loading,
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChangePasswordScreenPreview() {
    val navController = rememberNavController()
    PetJournalTheme {
        ChangePasswordScreen(navController = navController)
    }
}
