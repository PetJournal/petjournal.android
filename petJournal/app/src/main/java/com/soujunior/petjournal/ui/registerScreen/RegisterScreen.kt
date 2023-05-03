package com.soujunior.petjournal.ui.registerScreen

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.navigation.NavController
import com.soujunior.domain.entities.auth.RegisterModel
import com.soujunior.petjournal.ui.registerScreen.components.MyApp
import org.koin.androidx.compose.getViewModel

@Composable
fun RegisterScreen(navController: NavController) {
    val registerScreenViewModel: RegisterScreenViewModel = getViewModel()
    HandleRegisterResponse(navController, registerScreenViewModel)
    MyApp(navController, registerScreenViewModel)
}

@Composable
fun HandleRegisterResponse(
    navController: NavController,
    registerScreenViewModel: RegisterScreenViewModel
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    SideEffect {
        registerScreenViewModel.success.observe(lifecycleOwner) { success ->
            navController.navigate("login")
            Toast.makeText(context, "Faça seu login!", Toast.LENGTH_SHORT).show()
        }
        registerScreenViewModel.error.observe(lifecycleOwner) { error ->
            Toast.makeText(context, "Erro: $error", Toast.LENGTH_SHORT).show()
        }
    }
}

fun postForm(form: RegisterModel, RegisterScreenViewModel: RegisterScreenViewModel) {
    if (form.privacyPolicy) {
        RegisterScreenViewModel.postForm(form)
    }
}