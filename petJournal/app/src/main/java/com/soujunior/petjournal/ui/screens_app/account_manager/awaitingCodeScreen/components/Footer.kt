package com.soujunior.petjournal.ui.screens_app.account_manager.awaitingCodeScreen.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.components.Button2
import com.soujunior.petjournal.ui.components.Button3
import com.soujunior.petjournal.ui.screens_app.account_manager.awaitingCodeScreen.AwaitingCodeFormEvent
import com.soujunior.petjournal.ui.screens_app.account_manager.awaitingCodeScreen.AwaitingCodeViewModel
import com.soujunior.petjournal.ui.states.TaskState
import ir.kaaveh.sdpcompose.sdp

@Composable
fun Footer(navController: NavController, viewModel: AwaitingCodeViewModel) {
    val buttonIsEnable by viewModel.buttonIsEnable.collectAsState()
    val taskState by viewModel.taskState.collectAsState()
    val isDarkMode = isSystemInDarkTheme()
    Spacer(modifier = Modifier.padding(10.sdp))
    Row {
        Button3(
            submit = { navController.popBackStack() },
            enableButton = true,
            modifier = Modifier
                .weight(1f)
                .padding(end = 5.sdp),
            text = stringResource(R.string.back),
            buttonColor = ButtonDefaults.buttonColors(
                MaterialTheme.colorScheme.surface
            ),
            textColor = MaterialTheme.colorScheme.primary
        )
        Button2(
            buttonColor = if (isDarkMode) ButtonDefaults.buttonColors(MaterialTheme.colorScheme.onPrimary)
            else ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
            text = stringResource(R.string.send),
            border = null,
            textColor = if (isDarkMode) MaterialTheme.colorScheme.primary else Color.White,
            submit = { viewModel.onEvent(AwaitingCodeFormEvent.Submit) },
            enableButton = buttonIsEnable,
            modifier = Modifier
                .weight(1f)
                .padding(start = 5.sdp),
            isLoading = taskState is TaskState.Loading
        )
    }
}