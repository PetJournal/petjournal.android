package com.soujunior.petjournal.ui.accountManager.forgotPasswordScreen

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.soujunior.petjournal.ui.ValidationEvent
import com.soujunior.petjournal.ui.accountManager.forgotPasswordScreen.components.ForgotPassMain
import org.koin.androidx.compose.getViewModel



@Composable
fun ForgotPasswordScreen(navController: NavController) {
    val viewModel: ForgotPasswordScreenViewModel = getViewModel()
    val context = LocalContext.current
    LaunchedEffect(key1 = context) {
        viewModel.validationEvents.collect { event ->
            when (event) {
                is ValidationEvent.Success -> {
                    Toast.makeText(context, "Codigo de recuperação enviado para o email", Toast.LENGTH_LONG).show()
                    navController.navigate("awaitingCode")
                }

                is ValidationEvent.Failed -> {
                    Toast.makeText(context, "Erro ao enviar codigo", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    ForgotPassMain(navController, viewModel)
}


/*@Composable
fun ForgotPasswordScreen(navController: NavController) {
    val forgotPasswordScreenViewModel: ForgotPasswordScreenViewModel = getViewModel()
    HandleForgotPassResponse(navController, forgotPasswordScreenViewModel)
    ForgotPasswordScreenMain(navController, forgotPasswordScreenViewModel)
}

@Composable
fun HandleForgotPassResponse(
    navController: NavController,
    forgotPasswordScreenViewModel: ForgotPasswordScreenViewModel
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    SideEffect {
        forgotPasswordScreenViewModel.success.observe(lifecycleOwner) { success ->
            navController.navigate("awaitingCode")
            Toast.makeText(context, "Codigo enviado para o email de cadastro!", Toast.LENGTH_SHORT)
                .show()
        }
        forgotPasswordScreenViewModel.error.observe(lifecycleOwner) { error ->
            Toast.makeText(context, "Erro: $error", Toast.LENGTH_SHORT).show()
        }
    }
}

fun sendRequestToChangePassword(
    email: ForgotPasswordModel,
    forgotPasswordScreenViewModel: ForgotPasswordScreenViewModel
) {
    forgotPasswordScreenViewModel.sendRequestToChangePassword(email)
}*/






