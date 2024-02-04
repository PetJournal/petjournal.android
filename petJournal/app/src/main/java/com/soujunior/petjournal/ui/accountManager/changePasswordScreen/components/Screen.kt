package com.soujunior.petjournal.ui.accountManager.changePasswordScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.accountManager.changePasswordScreen.ChangePasswordFormEvent
import com.soujunior.petjournal.ui.accountManager.changePasswordScreen.ChangePasswordViewModel
import com.soujunior.petjournal.ui.components.Button2
import com.soujunior.petjournal.ui.components.CheckboxWithText
import com.soujunior.petjournal.ui.components.ImageLogo
import com.soujunior.petjournal.ui.components.InputText
import com.soujunior.petjournal.ui.states.TaskState

@Composable
fun Screen(viewModel: ChangePasswordViewModel) {
    val taskState by viewModel.taskState.collectAsState()
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(color = Color.Transparent, darkIcons = true)
    systemUiController.setNavigationBarColor(Color.Black)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(modifier = Modifier.padding(start = 20.dp, end = 20.dp)) {
            Spacer(modifier = Modifier.weight(0.1f))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                ImageLogo(
                    modifier = Modifier
                        .size(width = 150.dp, height = 150.dp)
                )
            }
            Spacer(modifier = Modifier.weight(0.1f))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                Text(
                    text = stringResource(id = R.string.change_password_title),
                    style = MaterialTheme.typography.displayMedium,
                    color = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.primary else Color.Unspecified,
                    textAlign = null
                )
            }
            Spacer(modifier = Modifier.weight(0.1f))
            Row(modifier = Modifier.padding(bottom = 16.dp)) {
                InputText(
                    textTop = stringResource(id = R.string.password_label),
                    textHint = stringResource(id = R.string.password_hint),
                    textValue = viewModel.state.password,
                    textError = viewModel.state.passwordError,
                    isPassword = true,
                    onEvent = { it: String ->
                        viewModel.onEvent(
                            ChangePasswordFormEvent.PasswordChanged(
                                it
                            )
                        )
                    },
                )
            }
            Row(modifier = Modifier.padding(top = 16.dp)) {
                InputText(
                    textTop = stringResource(id = R.string.confirm_password_label),
                    textHint = stringResource(id = R.string.confirm_password_hint),
                    textValue = viewModel.state.repeatedPassword,
                    textError = viewModel.state.repeatedPasswordError,
                    isPassword = true,
                    onEvent = { it: String ->
                        viewModel.onEvent(
                            ChangePasswordFormEvent.ConfirmPasswordChanged(
                                it
                            )
                        )
                    },
                )
            }
            Row(modifier = Modifier.padding(top = 16.dp)){
                CheckboxWithText(
                    textResourceId = R.string.disconnect_devices_question,
                    checkbox = viewModel.state.disconnectOtherDevices,
                    onEvent = { it: Boolean ->
                        viewModel.onEvent(
                            ChangePasswordFormEvent.DisconnectOtherDevices(
                                it
                            )
                        )
                    }
                )
            }
            Spacer(modifier = Modifier.weight(0.1f))
            Button2(
                submit = {
                    viewModel.onEvent(event = ChangePasswordFormEvent.Submit)
                },
                enableButton = viewModel.enableButton(),
                text = stringResource(id = R.string.reset_password_button),
                isLoading = taskState is TaskState.Loading
            )
            Spacer(modifier = Modifier.weight(0.4f))
        }
    }
}
