package com.soujunior.petjournal.ui.accountManager.registerScreen

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.soujunior.petjournal.ui.ValidationEvent
import com.soujunior.petjournal.ui.accountManager.registerScreen.components.Screen
import org.koin.androidx.compose.getViewModel

//TODO: Realizar chamada ao banco de dados para verificar se o email existe quando o botão for clicado
@Composable
fun RegisterScreen(navController: NavController) {
    val viewModel: RegisterViewModel = getViewModel()
    val context = LocalContext.current
    LaunchedEffect(key1 = context) {
        viewModel.validationEvents.collect { event ->
            when (event) {
                is ValidationEvent.Success -> {
                    Toast.makeText(context, viewModel.message.value, Toast.LENGTH_LONG).show()
                    navController.navigate("mainContent")
                }

                is ValidationEvent.Failed -> {
                    Toast.makeText(context, viewModel.message.value, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    Screen(viewModel)
}