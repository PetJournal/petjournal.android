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
    HandleRegisterResponse(registerScreenViewModel)
    MyApp(navController, registerScreenViewModel)
}

@Composable
fun HandleRegisterResponse(registerScreenViewModel: RegisterScreenViewModel) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    SideEffect {
        registerScreenViewModel.formSuccess.observe(lifecycleOwner) { success ->
            //TODO: Sucesso ->
            // Dialog com parabens
            // Chamar metodo de login logo em seguida
            // Ser encaminhado para a tela Home
            Toast.makeText(context, "Mensagem de sucesso: $success", Toast.LENGTH_SHORT).show()
        }
        registerScreenViewModel.formError.observe(lifecycleOwner) { error ->
            Toast.makeText(context, "Mensagem de Erro: $error", Toast.LENGTH_SHORT).show()
            //TODO: Falha ->
            // Exibir uma mensagem avisando do erro
        }
    }
}

fun postForm(form: RegisterModel, RegisterScreenViewModel: RegisterScreenViewModel) {
    if (form.privacyPolicy) {
        RegisterScreenViewModel.postForm(form)
    }
}