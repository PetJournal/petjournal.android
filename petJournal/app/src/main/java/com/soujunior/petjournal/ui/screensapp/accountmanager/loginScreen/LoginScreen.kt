package com.soujunior.petjournal.ui.screensapp.accountmanager.loginScreen

import android.annotation.SuppressLint
import android.widget.Toast
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.components.DashedInputText
import com.soujunior.petjournal.ui.screensapp.accountmanager.loginScreen.components.AccountConfirmationDialog
import com.soujunior.petjournal.ui.screensapp.accountmanager.loginScreen.components.Footer
import com.soujunior.petjournal.ui.screensapp.accountmanager.loginScreen.components.LoginHeader
import com.soujunior.petjournal.ui.screensapp.accountmanager.loginScreen.components.RememberPasswordAndForgotSection
import com.soujunior.petjournal.ui.theme.PetJournalTheme
import com.soujunior.petjournal.ui.util.ValidationEvent
import ir.kaaveh.sdpcompose.sdp
import org.koin.androidx.compose.getViewModel

@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun getLoginViewModelForPreview(): LoginViewModel {
    return if (LocalInspectionMode.current) {
        FakeLoginViewModel()
    } else {
        getViewModel()
    }
}

@Composable
fun LoginScreen(navController: NavController) {
    val viewModel: LoginViewModel = getLoginViewModelForPreview()
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        AccountConfirmationDialog(onDismiss = { showDialog = false })
    }

    LaunchedEffect(key1 = context) {
        viewModel.validationEvents.collect { event ->
            when (event) {
                is ValidationEvent.Success -> {
                    navController.popBackStack()
                    navController.navigate("mainContent")
                }

                is ValidationEvent.Failed -> {
                    if (event ==
                        ValidationEvent.Failed && viewModel.message.value ==
                        context.getString(
                            R.string.email_are_not_confirmed,
                        )
                    ) {
                        showDialog = true
                    } else if (event ==
                        ValidationEvent.Failed && viewModel.message.value ==
                        context.getString(
                            R.string.user_not_found,
                        ) || viewModel.message.value ==
                        context.getString(
                            R.string.Unauthorized,
                        )
                    ) {
                        Toast.makeText(
                            context,
                            R.string.incorrect_username_password,
                            Toast.LENGTH_LONG,
                        ).show()
                    } else {
                        Toast.makeText(
                            context,
                            context.getString(R.string.something_went_wrong_try_again_later),
                            Toast.LENGTH_LONG,
                        ).show()
                    }
                }
            }
        }
    }

    val systemUiController = rememberSystemUiController()

    systemUiController.setSystemBarsColor(
        color = Color.Transparent,
        darkIcons = true,
    )
    systemUiController.setNavigationBarColor(Color.Black)
    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ) {
            LoginHeader()
        }
        Column(
            modifier =
                Modifier
                    .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ) {
            LazyColumn(
                modifier =
                    Modifier
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
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
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
                        },
                    )
                }
                item {
                    RememberPasswordAndForgotSection(navController, viewModel)
                }
                item {
                    Spacer(modifier = Modifier.padding(top = 45.sdp))
                }
                item {
                    Footer(navController, viewModel)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginRegisterPreview() {
    val nav = rememberNavController()
    PetJournalTheme {
        LoginScreen(nav)
    }
}
