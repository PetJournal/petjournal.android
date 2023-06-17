package com.soujunior.petjournal.ui.accountManager.loginScreen

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.soujunior.petjournal.ui.ValidationEvent
import com.soujunior.petjournal.ui.accountManager.loginScreen.components.Screen
import org.koin.androidx.compose.getViewModel

@Composable
fun LoginScreen(navController: NavController) {
    val viewModel: LoginViewModel = getViewModel()
    val context = LocalContext.current
    Log.e("testar", "fora ->erro: ${viewModel.error.value}")
    Log.e("testar", "fora ->sucesso: ${viewModel.success.value}")
    LaunchedEffect(key1 = context) {
        viewModel.validationEvents.collect { event ->
            when (event) {
                is ValidationEvent.Success -> {
                    Toast.makeText(context, "Login bem sucedido", Toast.LENGTH_LONG).show()
                    Log.e("testar", "sucesso ->erro: ${viewModel.error.value}")
                    Log.e("testar", "sucesso ->sucesso: ${viewModel.success.value}")
                    navController.navigate("mainContent")
                }

                is ValidationEvent.Failed -> {
                    Log.e("testar", "falha ->erro: ${viewModel.error.value}")
                    Log.e("testar", "falha ->sucesso: ${viewModel.success.value}")
                    Toast.makeText(context, "Registro mal sucedido", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    Screen(navController, viewModel)
}