package com.soujunior.petjournal.ui.screensapp.accountmanager.loginScreen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.components.Checkbox
import com.soujunior.petjournal.ui.screensapp.accountmanager.loginScreen.FakeLoginViewModel
import com.soujunior.petjournal.ui.screensapp.accountmanager.loginScreen.LoginFormEvent
import com.soujunior.petjournal.ui.screensapp.accountmanager.loginScreen.LoginViewModel
import com.soujunior.petjournal.ui.theme.PetJournalTheme

@Composable
fun RememberPasswordAndForgotSection(
    navController: NavController,
    viewModel: LoginViewModel,
) {
    val textColor =
        if (isSystemInDarkTheme()) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.inverseSurface
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier =
            Modifier
                .fillMaxWidth(),
    ) {
        Column(content = {
            Checkbox(
                text = stringResource(R.string.remember_label),
                radioButtonSelected = viewModel.state.rememberPassword,
                onEvent = { it: Boolean ->
                    viewModel.onEvent(
                        LoginFormEvent.RememberPassword(
                            it,
                        ),
                    )
                },
            )
        })
        Column(
            modifier = Modifier.fillMaxWidth(),
            content = {
                Text(
                    text = stringResource(id = R.string.forgot_password_label),
                    style =
                        MaterialTheme.typography.labelLarge.copy(
                            textDecoration = TextDecoration.Underline,
                        ),
                    textAlign = TextAlign.End,
                    modifier =
                        Modifier
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() },
                                onClick = {
                                    navController.navigate("forgotPassword")
                                },
                            )
                            .testTag("link_to_forgotPassword")
                            .align(Alignment.End),
                    color = textColor,
                )
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RememberPasswordAndForgotSectionPreview() {
    val navController = rememberNavController()
    PetJournalTheme {
        RememberPasswordAndForgotSection(
            navController = navController,
            viewModel = FakeLoginViewModel(),
        )
    }
}
