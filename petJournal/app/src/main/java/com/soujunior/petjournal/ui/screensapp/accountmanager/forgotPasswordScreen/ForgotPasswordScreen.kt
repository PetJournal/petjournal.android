package com.soujunior.petjournal.ui.screensapp.accountmanager.forgotPasswordScreen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
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
import com.soujunior.petjournal.ui.components.HeaderImageLogoImagePasswordAndTitle
import com.soujunior.petjournal.ui.components.InputText
import com.soujunior.petjournal.ui.screensapp.accountmanager.forgotPasswordScreen.components.Footer
import com.soujunior.petjournal.ui.theme.PetJournalTheme
import com.soujunior.petjournal.ui.util.ValidationEvent
import ir.kaaveh.sdpcompose.sdp
import org.koin.androidx.compose.getViewModel

@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun getForgotPasswordViewModelForPreview(): ForgotPasswordViewModel {
    return if (LocalInspectionMode.current) {
        FakeForgotPasswordViewModel()
    } else {
        getViewModel()
    }
}

@Composable
fun ForgotPasswordScreen(navController: NavController) {
    val viewModel: ForgotPasswordViewModel = getForgotPasswordViewModelForPreview()
    val context = LocalContext.current

    LaunchedEffect(key1 = context) {
        viewModel.validationEvents.collect { event ->
            when (event) {
                is ValidationEvent.Success -> {
                    navController.navigate("awaitingCode/${viewModel.state.email}")
                }

                is ValidationEvent.Failed -> {
                    Toast.makeText(context, viewModel.message.value, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    val systemUiController = rememberSystemUiController()
    val darkIcons = !isSystemInDarkTheme()

    SideEffect {
        systemUiController.setSystemBarsColor(
            color = Color.Transparent,
            darkIcons = darkIcons,
        )
    }

    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .statusBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ) {
            HeaderImageLogoImagePasswordAndTitle(
                showImage = false,
                subText = stringResource(R.string.reset_password_in_two_steps),
                title = stringResource(R.string.forgot_password),
                spaceBetween = 40.sdp,
                styleTitle = MaterialTheme.typography.headlineLarge,
            )
            LazyColumn(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(start = 20.sdp, end = 20.sdp, top = 40.sdp)
                        .navigationBarsPadding(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center,
            ) {
                item {
                    InputText(
                        titleText = stringResource(R.string.what_is_your_registered_email_address),
                        requiredField = true,
                        placeholderText = stringResource(R.string.email_hint),
                        textValue = viewModel.state.email,
                        textError = viewModel.state.emailError,
                        isError = !viewModel.state.emailError.isNullOrEmpty(),
                        onEvent = { it: String ->
                            viewModel.onEvent(
                                ForgotPasswordFormEvent.EmailChanged(it),
                            )
                        },
                    )
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
fun ForgotPasswordScreenPreview() {
    val navController = rememberNavController()
    PetJournalTheme {
        ForgotPasswordScreen(navController = navController)
    }
}
