package com.soujunior.petjournal.ui.accountManager.loginScreen

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
    LaunchedEffect(key1 = context) {
        viewModel.validationEvents.collect { event ->
            when (event) {
                is ValidationEvent.Success -> {
                    Toast.makeText(
                        context,
                        "Login bem sucedido > ${viewModel.message.value}",
                        Toast.LENGTH_LONG
                    ).show()
                    navController.navigate("mainContent")
                }

                is ValidationEvent.Failed -> {
                    Toast.makeText(
                        context,
                        "falha no login! > ${viewModel.message.value}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
    Screen(navController, viewModel)
}