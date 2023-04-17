package com.soujunior.petjournal.ui.registerScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.navigation.NavController
import com.soujunior.domain.entities.auth.RegisterModel
import com.soujunior.petjournal.ui.registerScreen.components.MyApp
import org.koin.androidx.compose.getViewModel

@Composable
fun RegisterScreen(navController: NavController) {
    val registerScreenViewModel: RegisterScreenViewModel = getViewModel()
    val lifecycleOwner = LocalLifecycleOwner.current

    SideEffect {
        registerScreenViewModel.formSuccess.observe(lifecycleOwner) { success ->
            //TODO: Sucesso ->
            // Dialog com parabens
            // Chamar metodo de login logo em seguida
            // Ser encaminhado para a tela Home
        }
        registerScreenViewModel.formError.observe(lifecycleOwner) { error ->
            //TODO: Falha ->
            // Exibir uma mensagem avisando do erro
        }
    }
    MyApp(navController, registerScreenViewModel)
}

fun postForm(form: RegisterModel, RegisterScreenViewModel: RegisterScreenViewModel) {
    if (form.privacyPolicy) {
        RegisterScreenViewModel.postForm(form)
    }
}