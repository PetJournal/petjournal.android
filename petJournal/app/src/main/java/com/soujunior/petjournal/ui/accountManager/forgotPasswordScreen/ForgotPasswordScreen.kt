package com.soujunior.petjournal.ui.accountManager.forgotPasswordScreen

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.soujunior.petjournal.ui.ValidationEvent
import com.soujunior.petjournal.ui.accountManager.forgotPasswordScreen.components.Screen
import org.koin.androidx.compose.getViewModel

@Composable
fun ForgotPasswordScreen(navController: NavController) {
    val viewModel: ForgotPasswordViewModel = getViewModel()
    val context = LocalContext.current

    LaunchedEffect(key1 = context) {
        viewModel.validationEvents.collect { event ->
            when (event) {
                is ValidationEvent.Success -> {
                    Toast.makeText(context, "Codigo de recuperação enviado para o email", Toast.LENGTH_LONG).show()
                    navController.navigate("awaitingCode")
                }
                is ValidationEvent.Failed -> {
                    Toast.makeText(context, "Erro ao enviar codigo", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    Screen(navController, viewModel)
}