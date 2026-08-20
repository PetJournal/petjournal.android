package com.soujunior.petjournal.ui.screensapp.accountmanager.registerScreen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.components.Button2
import com.soujunior.petjournal.ui.components.Button3
import com.soujunior.petjournal.ui.components.InputText
import com.soujunior.petjournal.ui.components.PrivacyPolicyCheckbox
import com.soujunior.petjournal.ui.components.ScaffoldCustom
import com.soujunior.petjournal.ui.components.mask.mobileNumberFilter
import com.soujunior.petjournal.ui.states.TaskState
import com.soujunior.petjournal.ui.theme.PetJournalTheme
import com.soujunior.petjournal.ui.util.ValidationEvent
import ir.kaaveh.sdpcompose.sdp
import org.koin.androidx.compose.getViewModel

@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun getRegisterGuardianViewModelForPreview(): RegisterViewModel {
    return if (LocalInspectionMode.current) {
        FakeRegisterViewModel()
    } else {
        getViewModel()
    }
}

@Composable
fun RegisterScreen(navController: NavController) {
    val viewModel: RegisterViewModel = getRegisterGuardianViewModelForPreview()
    val context = LocalContext.current

    val taskState by viewModel.taskState.collectAsState()
    val systemUiController = rememberSystemUiController()
    val isDarkMode = isSystemInDarkTheme()
    systemUiController.setSystemBarsColor(color = Color.Transparent, darkIcons = true)
    systemUiController.setNavigationBarColor(Color.Black)

    LaunchedEffect(key1 = context) {
        viewModel.validationEvents.collect { event ->
            when (event) {
                is ValidationEvent.Success -> {
                    navController.navigate("login")
                }

                is ValidationEvent.Failed -> {
                    Toast.makeText(context, viewModel.message.value, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.onPrimary),
    ) {
        ScaffoldCustom(
            navigationUp = navController,
            modifier =
                Modifier
                    .statusBarsPadding()
                    .navigationBarsPadding(),
            contentToUse = { it ->
                Box(modifier = Modifier.fillMaxSize()) {
                    Image(
                        painter = painterResource(id = R.drawable.rastro),
                        contentDescription = null,
                        modifier = Modifier.fillMaxWidth(),
                        contentScale = ContentScale.FillWidth,
                    )
                    LazyColumn(
                        modifier =
                            Modifier
                                .fillMaxSize()
                                .padding(
                                    start = 20.sdp,
                                    end = 20.sdp,
                                ),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Top,
                    ) {
                        item {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier =
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(end = 24.sdp, bottom = 10.sdp, top = 10.sdp),
                            ) {
                                Text(
                                    text = stringResource(R.string.register_),
                                    style = MaterialTheme.typography.headlineLarge,
                                    color = MaterialTheme.colorScheme.primary,
                                )
                            }
                        }
                        item {
                            InputText(
                                requiredField = true,
                                titleText = stringResource(id = R.string.name),
                                placeholderText = stringResource(id = R.string.eg_enter_your_first_name),
                                textValue = viewModel.state.name,
                                textError = viewModel.state.nameError,
                                isError = !viewModel.state.nameError.isNullOrEmpty(),
                                modifier =
                                    Modifier
                                        .fillMaxWidth()
                                        .testTag("input_name"),
                                onEvent = { it: String ->
                                    viewModel.onEvent(
                                        RegisterFormEvent.NameChanged(
                                            it,
                                        ),
                                    )
                                },
                            )
                        }
                        item {
                            InputText(
                                requiredField = true,
                                titleText = stringResource(id = R.string.lastname),
                                placeholderText = stringResource(id = R.string.eg_enter_your_last_name),
                                textValue = viewModel.state.lastName,
                                textError = viewModel.state.lastNameError,
                                isError = !viewModel.state.lastNameError.isNullOrEmpty(),
                                modifier =
                                    Modifier
                                        .fillMaxWidth()
                                        .testTag("input_lastname"),
                                onEvent = { it: String ->
                                    viewModel.onEvent(
                                        RegisterFormEvent.LastNameChanged(
                                            it,
                                        ),
                                    )
                                },
                            )
                        }
                        item {
                            InputText(
                                requiredField = true,
                                titleText = stringResource(id = R.string.email),
                                placeholderText = stringResource(id = R.string.eg_email),
                                textValue = viewModel.state.email,
                                textError = viewModel.state.emailError,
                                isError = !viewModel.state.emailError.isNullOrEmpty(),
                                modifier =
                                    Modifier
                                        .fillMaxWidth()
                                        .testTag("input_email"),
                                onEvent = { it: String ->
                                    viewModel.onEvent(
                                        RegisterFormEvent.EmailChanged(
                                            it,
                                        ),
                                    )
                                },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                            )
                        }
                        item {
                            InputText(
                                requiredField = true,
                                titleText = stringResource(id = R.string.phone),
                                placeholderText = stringResource(id = R.string.eg_phone),
                                textValue = viewModel.state.phone,
                                textError = viewModel.state.phoneError,
                                isError = !viewModel.state.phoneError.isNullOrEmpty(),
                                hasAMask = true,
                                modifier =
                                    Modifier
                                        .fillMaxWidth()
                                        .testTag("input_phone"),
                                onEvent = { it: String ->
                                    if (it.length <= 11) {
                                        viewModel.onEvent(
                                            RegisterFormEvent.PhoneChanged(
                                                it,
                                            ),
                                        )
                                    }
                                },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                visualTransformation = { mobileNumberFilter(it) },
                            )
                        }
                        item {
                            InputText(
                                requiredField = true,
                                titleText = stringResource(id = R.string.password),
                                placeholderText = stringResource(id = R.string.eg_password),
                                textValue = viewModel.state.password,
                                textError = viewModel.state.passwordError,
                                isError = !viewModel.state.passwordError.isNullOrEmpty(),
                                modifier =
                                    Modifier
                                        .fillMaxWidth()
                                        .testTag("input_password"),
                                isPassword = true,
                                onEvent = { it: String ->
                                    viewModel.onEvent(
                                        RegisterFormEvent.PasswordChanged(
                                            it,
                                        ),
                                    )
                                },
                            )
                        }
                        item {
                            InputText(
                                requiredField = true,
                                titleText = stringResource(id = R.string.confirm_password),
                                placeholderText = stringResource(id = R.string.confirm_password),
                                textValue = viewModel.state.repeatedPassword,
                                textError = viewModel.state.repeatedPasswordError,
                                isError = !viewModel.state.repeatedPasswordError.isNullOrEmpty(),
                                modifier =
                                    Modifier
                                        .fillMaxWidth()
                                        .testTag("input_confirm_password"),
                                isPassword = true,
                                onEvent = { it: String ->
                                    viewModel.onEvent(
                                        RegisterFormEvent.ConfirmPasswordChanged(
                                            it,
                                        ),
                                    )
                                },
                            )
                        }
                        item {
                            PrivacyPolicyCheckbox(
                                valueChecked = viewModel.state.privacyPolicy,
                                modifier =
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(top = 18.dp)
                                        .testTag("checkbox"),
                                onEvent = { it: Boolean ->
                                    viewModel.onEvent(
                                        RegisterFormEvent.PrivacyPolicyChanged(
                                            it,
                                        ),
                                    )
                                },
                            )
                        }
                        item { Spacer(modifier = Modifier.height(5.sdp)) }
                        item {
                            Row(
                                Modifier.padding(top = 50.sdp, bottom = 50.sdp),
                            ) {
                                Button3(
                                    submit = { navController.popBackStack() },
                                    enableButton = true,
                                    modifier =
                                        Modifier
                                            .weight(1f)
                                            .padding(end = 7.5.dp),
                                    text = stringResource(R.string.back),
                                    buttonColor =
                                        ButtonDefaults.buttonColors(
                                            MaterialTheme.colorScheme.surface,
                                        ),
                                    textColor = MaterialTheme.colorScheme.primary,
                                )
                                Button2(
                                    text = stringResource(id = R.string.register),
                                    border = null,
                                    buttonColor = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
                                    textColor = if (isDarkMode) MaterialTheme.colorScheme.onPrimary else Color.White,
                                    submit = {
                                        viewModel.onEvent(RegisterFormEvent.Submit)
                                    },
                                    enableButton = viewModel.enableButton(),
                                    modifier =
                                        Modifier
                                            .weight(1f)
                                            .padding(start = 7.5.dp)
                                            .testTag("button_register"),
                                    isLoading = taskState is TaskState.Loading,
                                )
                            }
                        }
                    }
                }
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    val nav = rememberNavController()
    PetJournalTheme {
        RegisterScreen(nav)
    }
}
