package com.soujunior.petjournal.ui.accountManager.changePasswordScreen

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.soujunior.petjournal.ui.ValidationEvent
import com.soujunior.petjournal.ui.accountManager.changePasswordScreen.components.Screen
import org.koin.androidx.compose.getViewModel

@Composable
fun ChangePasswordScreen(navController: NavController) {
    val viewModel: ChangePasswordViewModel = getViewModel()
    val context = LocalContext.current
    LaunchedEffect(key1 = context) {
        viewModel.validationEvents.collect { event ->
            when (event) {
                is ValidationEvent.Success -> {
                    Toast.makeText(context, "Voce trocou sua senha!", Toast.LENGTH_LONG).show()
                    navController.navigate("mainContent")
                }

                is ValidationEvent.Failed -> {
                    Toast.makeText(context, "Não foi possivel trocar a senha!", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    Screen(viewModel)
}