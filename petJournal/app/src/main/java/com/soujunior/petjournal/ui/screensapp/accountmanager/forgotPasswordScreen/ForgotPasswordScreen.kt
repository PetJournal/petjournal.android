package com.soujunior.petjournal.ui.screensapp.accountmanager.forgotPasswordScreen

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.soujunior.petjournal.ui.screensapp.accountmanager.forgotPasswordScreen.components.Screen
import com.soujunior.petjournal.ui.util.ValidationEvent
import org.koin.androidx.compose.getViewModel

@Composable
fun ForgotPasswordScreen(navController: NavController) {
    val viewModel: ForgotPasswordViewModel = getViewModel()
    val context = LocalContext.current

    LaunchedEffect(key1 = context) {
        viewModel.validationEvents.collect { event ->
            when (event) {
                is ValidationEvent.Success -> {
                    navController.navigate("awaitingCode/${viewModel.state.email}")
                }

                is ValidationEvent.Failed -> {
                    Toast.makeText(context, viewModel.message.value, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    Screen(navController, viewModel)
}
