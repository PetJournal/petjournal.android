package com.soujunior.petjournal.ui.accountManager.forgotPasswordScreen.components

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.soujunior.petjournal.ui.accountManager.forgotPasswordScreen.ForgotPasswordFormEvent
import com.soujunior.petjournal.ui.accountManager.forgotPasswordScreen.ForgotPasswordViewModel
import com.soujunior.petjournal.ui.components.Button2

@Composable
fun Footer(
    navController: NavController,
    viewModel: ForgotPasswordViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row {
            Button2(
                text = "Continuar",
                border = null,
                submit = {
                    viewModel.onEvent(ForgotPasswordFormEvent.Submit)
                },
                enableButton = viewModel.enableButton(),
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
                submit = { navController.navigate("login") },
                enableButton = true,
                modifier = Modifier.size(height = 50.dp, width = 240.dp).padding(bottom = 50.dp),
                buttonColor = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                textColor = MaterialTheme.colorScheme.primary,
                )
        }
    }
}