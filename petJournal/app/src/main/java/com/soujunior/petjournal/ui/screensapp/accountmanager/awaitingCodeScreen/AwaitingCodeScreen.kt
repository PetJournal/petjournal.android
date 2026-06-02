package com.soujunior.petjournal.ui.screensapp.accountmanager.awaitingCodeScreen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.components.HeaderImageLogoImagePasswordAndTitle
import com.soujunior.petjournal.ui.screensapp.accountmanager.awaitingCodeScreen.components.Footer
import com.soujunior.petjournal.ui.screensapp.accountmanager.awaitingCodeScreen.components.OTPTextField
import com.soujunior.petjournal.ui.theme.PetJournalTheme
import com.soujunior.petjournal.ui.util.ValidationEvent
import ir.kaaveh.sdpcompose.sdp
import org.koin.androidx.compose.getViewModel

@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun getAwaitingCodeViewModelForPreview(): AwaitingCodeViewModel {
    return if (LocalInspectionMode.current) {
        FakeAwaitingCodeViewModel()
    } else {
        getViewModel()
    }
}

@Composable
fun AwaitingCodeScreen(
    arg: String?,
    navController: NavController,
) {
    val viewModel: AwaitingCodeViewModel = getAwaitingCodeViewModelForPreview()
    val context = LocalContext.current

    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(color = Color.Transparent, darkIcons = true)
    systemUiController.setNavigationBarColor(Color.Black)
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = context) {
        if (!arg.isNullOrBlank()) {
            viewModel.onEvent(AwaitingCodeFormEvent.EmailChanged(arg))
        } else {
            Toast.makeText(context, "Email inválido", Toast.LENGTH_SHORT).show()
            navController.navigateUp()
        }
        viewModel.validationEvents.collect { event ->
            when (event) {
                is ValidationEvent.Success -> {
                    navController.navigate("changePassword")
                }

                is ValidationEvent.Failed -> {
                    Toast.makeText(context, viewModel.message.value, Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }

    Column(
        modifier =
            Modifier
                .statusBarsPadding()
                .navigationBarsPadding()
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        HeaderImageLogoImagePasswordAndTitle(
            showImage = false,
            title = stringResource(R.string.txt_we_just_sent_a_code_to_your_email),
            spaceBetween = 10.sdp,
            subText = stringResource(R.string.txt_enter_the_6_digit_verification_code_sent_to_your_email_in_the_field_below),
            textAlign = TextAlign.Center,
            styleTitle = MaterialTheme.typography.headlineLarge,
        )
        LazyColumn(
            modifier =
                Modifier
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
                                code,
                            ),
                        )
                    },
                    textError = state.codeOTPError,
                    viewModel = viewModel,
                )
            }
            item {
                Box(
                    modifier = Modifier.fillMaxWidth().padding(top = 25.sdp),
                    contentAlignment = Alignment.TopStart,
                ) {
                    Text(
                        text = stringResource(R.string.txt_tip_If_you_dont_find_the_email_in_your_inbox_check_your_spam_folder),
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Start,
                        color =
                            if (isSystemInDarkTheme()) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.onBackground
                            },
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

@Preview(showBackground = true)
@Composable
fun AwaitingCodeScreenPreview() {
    val navController = rememberNavController()
    PetJournalTheme {
        AwaitingCodeScreen(
            arg = "test@example.com",
            navController = navController,
        )
    }
}
