package com.soujunior.petjournal.ui.awaitingCodeScreen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.soujunior.domain.entities.auth.AwaitingCodeModel
import com.soujunior.petjournal.ui.awaitingCodeScreen.components.ButtonSend
import com.soujunior.petjournal.ui.awaitingCodeScreen.components.MyApp
import com.soujunior.petjournal.ui.loginScreen.components.ButtonLogin
import com.soujunior.petjournal.ui.registerScreen.components.ImageLogo
import com.soujunior.petjournal.ui.util.isEmail
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


