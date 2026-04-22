package com.soujunior.petjournal.ui.screensapp.accountmanager.loginScreen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.components.Button2
import com.soujunior.petjournal.ui.screensapp.accountmanager.loginScreen.FakeLoginViewModel
import com.soujunior.petjournal.ui.screensapp.accountmanager.loginScreen.LoginFormEvent
import com.soujunior.petjournal.ui.screensapp.accountmanager.loginScreen.LoginViewModel
import com.soujunior.petjournal.ui.states.TaskState
import com.soujunior.petjournal.ui.theme.PetJournalTheme
import ir.kaaveh.sdpcompose.sdp

@Composable
fun FooterLogin(
    navController: NavController,
    viewModel: LoginViewModel,
) {
    val isDarkMode = isSystemInDarkTheme()
    val taskState by viewModel.taskState.collectAsState()
    val annotatedText =
        buildAnnotatedString {
            append(stringResource(R.string.nao_tem_uma_conta))
            withStyle(
                style =
                    SpanStyle(
                        color = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.inverseSurface,
                        textDecoration = TextDecoration.Underline,
                    ),
            ) {
                append(stringResource(R.string.cadastre_se))
            }
        }
    Column(
        modifier =
            Modifier
                .fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally),
        ) {
            Text(
                text = annotatedText,
                style = MaterialTheme.typography.bodyMedium,
                modifier =
                    Modifier
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() },
                            onClick = { navController.navigate("register") },
                        )
                        .align(CenterVertically)
                        .testTag("link_to_register"),
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
        Spacer(modifier = Modifier.padding(top = 20.sdp))
        Row(modifier = Modifier.fillMaxWidth()) {
            Button2(
                text = "Login",
                border = null,
                buttonColor =
                    if (isDarkMode) {
                        ButtonDefaults.buttonColors(MaterialTheme.colorScheme.onPrimary)
                    } else {
                        ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
                    },
                textColor = if (isDarkMode) MaterialTheme.colorScheme.primary else Color.White,
                submit = { viewModel.onEvent(LoginFormEvent.Submit) },
                enableButton = viewModel.enableButton(),
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(start = 40.sdp, end = 40.sdp)
                        .testTag("button_continue"),
                isLoading = taskState is TaskState.Loading,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FooterLoginPreview() {
    val navController = rememberNavController()
    PetJournalTheme {
        FooterLogin(
            navController = navController,
            viewModel = FakeLoginViewModel(),
        )
    }
}
