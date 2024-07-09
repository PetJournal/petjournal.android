package com.soujunior.petjournal.ui.screens_app.account_manager.loginScreen.components

import androidx.compose.foundation.clickable
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.components.Button2
import com.soujunior.petjournal.ui.screens_app.account_manager.loginScreen.LoginFormEvent
import com.soujunior.petjournal.ui.screens_app.account_manager.loginScreen.LoginViewModel
import com.soujunior.petjournal.ui.states.TaskState

@Composable
fun Footer(
    navController: NavController,
    viewModel: LoginViewModel
) {
    val taskState by viewModel.taskState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        //TODO("Extrair string")
        Row(modifier = Modifier.fillMaxWidth()) {
            Button2(
                text = "Continuar",
                border = null,
                submit = { viewModel.onEvent(LoginFormEvent.Submit) },
                enableButton = viewModel.enableButton(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 40.dp, end = 40.dp)
                    .testTag("button_continue"),
                buttonColor = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                isLoading = taskState is TaskState.Loading
            )
        }
        Spacer(modifier = Modifier.padding(top = 20.dp))
        //TODO("Extrair string")
        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = stringResource(R.string.nao_tem_uma_conta_cadastre_se),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .clickable(onClick = { navController.navigate("register") })
                    .align(CenterVertically)
                    .testTag("link_to_register"),
                color = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.primary else Color.Unspecified
            )
        }
    }
}