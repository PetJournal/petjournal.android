package com.soujunior.petjournal.ui.loginScreen

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.navigation.NavController
import com.soujunior.domain.entities.auth.LoginModel
import com.soujunior.petjournal.ui.loginScreen.components.MyApp
import org.koin.androidx.compose.getViewModel

@Composable
fun LoginScreen(navController: NavController) {
    val loginScreenViewModel: LoginScreenViewModel = getViewModel()
    HandleLoginResponse(navController, loginScreenViewModel)
    MyApp(navController)
}

@Composable
fun HandleLoginResponse(navController: NavController, loginScreenViewModel: LoginScreenViewModel) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    SideEffect {
        loginScreenViewModel.success.observe(lifecycleOwner) { success ->
            Toast.makeText(context, "Sucesso: $success", Toast.LENGTH_LONG + 3).show()
            navController.navigate("mainContent")
        }
        loginScreenViewModel.error.observe(lifecycleOwner) { error ->
            Toast.makeText(context, "Erro: $error", Toast.LENGTH_LONG + 3).show()
        }
    }
}

fun postFormLogin(form: LoginModel, loginScreenViewModel: LoginScreenViewModel) {
    loginScreenViewModel.postForm(form)
}