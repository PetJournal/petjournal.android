package com.soujunior.petjournal.ui.accountManager.changePasswordScreen.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.accountManager.changePasswordScreen.ChangePasswordFormEvent
import com.soujunior.petjournal.ui.accountManager.changePasswordScreen.ChangePasswordViewModel
import com.soujunior.petjournal.ui.components.Button2
import com.soujunior.petjournal.ui.components.CheckboxWithText
import com.soujunior.petjournal.ui.components.CreateTitleAndImageLogo
import com.soujunior.petjournal.ui.components.InputText
import com.soujunior.petjournal.ui.states.TaskState

@Composable
fun Screen(
    viewModel: ChangePasswordViewModel
) {
    val taskState by viewModel.taskState.collectAsState()

    Box(modifier = Modifier.fillMaxWidth()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp),
        ) {
            item {
                CreateTitleAndImageLogo(
                    title = stringResource(id = R.string.change_password_title),
                    styleTitle = MaterialTheme.typography.displayMedium,
                    spaceTop = 60.dp,
                    spaceBetween = 40.dp,
                    spaceBottom = 30.dp
                )
            }
            item {
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
            item { Spacer(modifier = Modifier.height(30.dp)) }
            item {
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
            item { Spacer(modifier = Modifier.height(30.dp)) }
            item {
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
            item { Spacer(modifier = Modifier.height(30.dp)) }
            item {
                Button2(
                    submit = {
                        viewModel.onEvent(event = ChangePasswordFormEvent.Submit)
                    },
                    enableButton = viewModel.enableButton(),
                    text = stringResource(id = R.string.reset_password_button),
                    isLoading = taskState is TaskState.Loading
                )
            }
        }
    }
}
