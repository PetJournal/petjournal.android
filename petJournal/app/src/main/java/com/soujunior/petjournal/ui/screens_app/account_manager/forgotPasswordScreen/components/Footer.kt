package com.soujunior.petjournal.ui.screens_app.account_manager.forgotPasswordScreen.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.components.Button2
import com.soujunior.petjournal.ui.components.Button3
import com.soujunior.petjournal.ui.screens_app.account_manager.forgotPasswordScreen.ForgotPasswordFormEvent
import com.soujunior.petjournal.ui.screens_app.account_manager.forgotPasswordScreen.ForgotPasswordViewModel
import com.soujunior.petjournal.ui.states.TaskState

@Composable
fun Footer(
    navController: NavController,
    viewModel: ForgotPasswordViewModel
) {
    val taskState by viewModel.taskState.collectAsState()

    Column(
        modifier = Modifier.padding(bottom = 40.dp)
    ) {
        Row(
            Modifier.padding(20.dp)
        ) {
            Button3(
                submit = { navController.popBackStack() },
                enableButton = true,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 5.dp),
                text = stringResource(R.string.back),
                buttonColor = ButtonDefaults.buttonColors(
                    MaterialTheme.colorScheme.surface
                ),
                textColor = MaterialTheme.colorScheme.primary
            )
            Button2(

                text = stringResource(R.string.send),
                border = null,
                submit = {
                    viewModel.onEvent(ForgotPasswordFormEvent.Submit)
                },
                enableButton = viewModel.enableButton(),
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 5.dp),
                isLoading = taskState is TaskState.Loading
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FooterPreview() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row {
            Button2(
                text = "Continuar",
                border = null,
                submit = {

                },
                enableButton = true,
                modifier = Modifier.size(height = 50.dp, width = 240.dp),
                buttonColor = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }
        Spacer(modifier = Modifier.padding(top = 10.dp))
        Row {
            Button2(
                text = "Cancelar",
                border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
                submit = { },
                enableButton = true,
                modifier = Modifier
                    .size(height = 50.dp, width = 240.dp)
                    .padding(bottom = 50.dp),
                buttonColor = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                textColor = MaterialTheme.colorScheme.primary,
            )
        }
    }
}