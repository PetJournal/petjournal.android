package com.soujunior.petjournal.ui.forgotPasswordScreen

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.navigation.NavController
import com.soujunior.domain.entities.auth.ForgotPasswordModel
import com.soujunior.petjournal.ui.forgotPasswordScreen.components.ForgotPasswordScreenMain
import org.koin.androidx.compose.getViewModel

@Composable
fun ForgotPasswordScreen(navController: NavController) {
    val forgotPasswordScreenViewModel: ForgotPasswordScreenViewModel = getViewModel()
    HandleForgotPassResponse(navController, forgotPasswordScreenViewModel)
    ForgotPasswordScreenMain(navController,forgotPasswordScreenViewModel)
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
            Toast.makeText(context, "Codigo enviado para o email de cadastro!", Toast.LENGTH_SHORT).show()
        }
        forgotPasswordScreenViewModel.error.observe(lifecycleOwner) { error ->
            Toast.makeText(context, "Erro: $error", Toast.LENGTH_SHORT).show()
        }
    }
}

fun sendRequestToChangePassword(email: ForgotPasswordModel, forgotPasswordScreenViewModel : ForgotPasswordScreenViewModel) {
    //TODO:
    Log.e("testar", "${email.email}")
    forgotPasswordScreenViewModel.sendRequestToChangePassword(email)
}






