package com.soujunior.petjournal.ui.accountManager.loginScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.accountManager.loginScreen.LoginFormEvent
import com.soujunior.petjournal.ui.accountManager.loginScreen.LoginViewModel
import com.soujunior.petjournal.ui.components.RadioButtonWithText

@Composable
fun RememberPasswordAndForgotSection(navController: NavController, viewModel: LoginViewModel) {
    val textColor =
        if (isSystemInDarkTheme()) MaterialTheme.colorScheme.primary else Color.Unspecified
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(content = {
            RadioButtonWithText(
                textResourceId = R.string.remember_label,
                radioButtonSelected = viewModel.state.rememberPassword,
                onEvent = { it: Boolean ->
                    viewModel.onEvent(
                        LoginFormEvent.RememberPassword(
                            it
                        )
                    )
                },
            )
        })
        Column(
            modifier = Modifier.fillMaxWidth(),content = {
            Text(
                text = stringResource(id = R.string.forgot_password_label),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.End,
                modifier = Modifier
                    .clickable(
                        onClick = {
                            navController.navigate("forgotPassword")
                        }).testTag("link_to_forgotPassword")
                    .align(Alignment.End),
                color = textColor
            )
        })

    }
}