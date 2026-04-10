package com.soujunior.petjournal.ui.screensapp.accountmanager.awaitingCodeScreen

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.soujunior.petjournal.ui.screensapp.accountmanager.awaitingCodeScreen.components.Screen
import com.soujunior.petjournal.ui.util.ValidationEvent
import org.koin.androidx.compose.getViewModel

@Composable
fun AwaitingCodeScreen(
    arg: String?,
    navController: NavController,
) {
    val viewModel: AwaitingCodeViewModel = getViewModel()
    val context = LocalContext.current

    LaunchedEffect(key1 = context) {
        if (!arg.isNullOrBlank()) {
            viewModel.onEvent(AwaitingCodeFormEvent.EmailChanged(arg))
        } else {
            Toast.makeText(context, "Email inválido", Toast.LENGTH_SHORT).show()
            navController.navigateUp()
        }
        viewModel.validationEvents.collect { event ->
            when (event) {
                is ValidationEvent.Success -> {
                    navController.navigate("changePassword")
                }

                is ValidationEvent.Failed -> {
                    Toast.makeText(context, viewModel.message.value, Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }
    Screen(navController, viewModel)
}
