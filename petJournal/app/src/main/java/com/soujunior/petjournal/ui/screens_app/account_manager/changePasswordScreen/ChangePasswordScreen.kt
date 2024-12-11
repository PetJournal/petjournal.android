package com.soujunior.petjournal.ui.screens_app.account_manager.changePasswordScreen

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.screens_app.account_manager.changePasswordScreen.components.Screen
import com.soujunior.petjournal.ui.util.ValidationEvent
import org.koin.androidx.compose.getViewModel

@Composable
fun ChangePasswordScreen(navController: NavController) {
    val viewModel: ChangePasswordViewModel = getViewModel()
    val context = LocalContext.current
    LaunchedEffect(key1 = context) {
        viewModel.validationEvents.collect { event ->
            when (event) {
                is ValidationEvent.Success -> {
                    Toast.makeText(context, R.string.success, Toast.LENGTH_LONG).show()
                    navController.navigate("mainContent")
                }

                is ValidationEvent.Failed -> {
                    Toast.makeText(context, viewModel.message.value, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    Screen(viewModel, navController)
}