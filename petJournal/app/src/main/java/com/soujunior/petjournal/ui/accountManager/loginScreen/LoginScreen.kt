package com.soujunior.petjournal.ui.accountManager.loginScreen

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.accountManager.loginScreen.components.Screen
import com.soujunior.petjournal.ui.util.ValidationEvent
import org.koin.androidx.compose.getViewModel

@Composable
fun LoginScreen(navController: NavController) {
    val viewModel: LoginViewModel = getViewModel()
    val context = LocalContext.current

    LaunchedEffect(key1 = context) {
        viewModel.validationEvents.collect { event ->
            when (event) {
                is ValidationEvent.Success -> {
                    navController.popBackStack()
                    navController.navigate("mainContent")
                }

                is ValidationEvent.Failed -> {
                    Toast.makeText(
                        context,
                        R.string.incorrect_username_password,
                        Toast.LENGTH_LONG
                    ).show()
                }

                else -> {}
            }
        }
    }
    Screen(navController, viewModel)
}