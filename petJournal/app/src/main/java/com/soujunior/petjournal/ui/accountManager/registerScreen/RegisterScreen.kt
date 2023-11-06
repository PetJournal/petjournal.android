package com.soujunior.petjournal.ui.accountManager.registerScreen

import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.soujunior.petjournal.ui.util.ValidationEvent
import com.soujunior.petjournal.ui.accountManager.registerScreen.components.Screen
import org.koin.androidx.compose.getViewModel

@Composable
fun RegisterScreen(navController: NavController) {
    val viewModel: RegisterViewModel = getViewModel()
    val context = LocalContext.current

    LaunchedEffect(key1 = context) {
        viewModel.validationEvents.collect { event ->
            when (event) {
                is ValidationEvent.Success -> {
                    navController.navigate("login")
                }

                is ValidationEvent.Failed -> {
                    Toast.makeText(context, viewModel.message.value, Toast.LENGTH_LONG).show()
                    Log.e(TAG, "Layout register: " + viewModel.message.value)
                }
            }
        }
    }
    Screen(viewModel)
}