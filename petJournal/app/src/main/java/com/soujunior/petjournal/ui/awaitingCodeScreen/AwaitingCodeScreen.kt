package com.soujunior.petjournal.ui.awaitingCodeScreen

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.navigation.NavController
import com.soujunior.domain.entities.auth.AwaitingCodeModel
import com.soujunior.petjournal.ui.awaitingCodeScreen.components.MyApp
import org.koin.androidx.compose.getViewModel

@Composable

fun AwaitingCodeScreen(navController: NavController) {
    val awaitingCodeScreenViewModel: AwaitingCodeScreenViewModel = getViewModel()
    HandleAwaitingCodeResponse(navController, awaitingCodeScreenViewModel)
    MyApp(navController)
}

@Composable
fun HandleAwaitingCodeResponse(
    navController: NavController,
    awaitingCodeScreenViewModel: AwaitingCodeScreenViewModel
) {

    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    SideEffect {
        awaitingCodeScreenViewModel.success.observe(lifecycleOwner) { success ->
            Toast.makeText(context, "Sucesso: $success", Toast.LENGTH_LONG + 3).show()
            navController.navigate("mainContent")
        }
        awaitingCodeScreenViewModel.error.observe(lifecycleOwner) { error ->
            Toast.makeText(context, "Erro: $error", Toast.LENGTH_LONG + 3).show()
        }
    }
}

fun postOtpVerificationAwaitingCode(
    code: AwaitingCodeModel,
    awaitingCodeScreenViewModel: AwaitingCodeScreenViewModel
) {
    awaitingCodeScreenViewModel.postOtpVerification(code)
}