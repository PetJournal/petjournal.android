package com.soujunior.petjournal.ui.screens_app.account_manager.loginScreen

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.screens_app.account_manager.loginScreen.components.AccountConfirmationDialog
import com.soujunior.petjournal.ui.screens_app.account_manager.loginScreen.components.Screen
import com.soujunior.petjournal.ui.util.ValidationEvent
import org.koin.androidx.compose.getViewModel
import androidx.compose.runtime.*

@Composable
fun LoginScreen(navController: NavController) {
    val viewModel: LoginViewModel = getViewModel()
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        AccountConfirmationDialog(onDismiss = { showDialog = false })
    }

    LaunchedEffect(key1 = context) {
        viewModel.validationEvents.collect { event ->
            when (event) {
                is ValidationEvent.Success -> {
                    navController.popBackStack()
                    navController.navigate("mainContent")
                }

                is ValidationEvent.Failed -> {
                    if (event == ValidationEvent.Failed && viewModel.message.value == context.getString(
                            R.string.email_are_not_confirmed
                        )
                    ) {
                        showDialog = true
                    } else if (event == ValidationEvent.Failed && viewModel.message.value == context.getString(
                            R.string.user_not_found) || viewModel.message.value == context.getString(
                            R.string.Unauthorized
                        )
                    ) {
                        Toast.makeText(
                            context,
                            R.string.incorrect_username_password,
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        Toast.makeText(
                            context,
                            context.getString(R.string.something_went_wrong_try_again_later),
                            Toast.LENGTH_LONG
                        ).show()
                    }

                }
            }
        }
    }
    Screen(navController)
}