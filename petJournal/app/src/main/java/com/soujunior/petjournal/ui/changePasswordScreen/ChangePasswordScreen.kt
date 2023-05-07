package com.soujunior.petjournal.ui.changePasswordScreen

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.navigation.NavController
import com.soujunior.domain.entities.auth.PasswordModel
import com.soujunior.petjournal.ui.changePasswordScreen.components.MyApp
import org.koin.androidx.compose.getViewModel

@Composable
fun ChangePasswordScreen(navController: NavController) {
    val changePasswordViewModel: ChangePasswordViewModel = getViewModel()
    HandlerChangePasswordResponse(navController, changePasswordViewModel)
    MyApp(changePasswordViewModel)
}

@Composable
fun HandlerChangePasswordResponse(
    navController: NavController,
    changePasswordViewModel: ChangePasswordViewModel
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    SideEffect {
        changePasswordViewModel.success.observe(lifecycleOwner) { success ->
            Toast.makeText(context, "Sucesso: $success", Toast.LENGTH_LONG + 3).show()
            navController.navigate("mainContent")
        }
        changePasswordViewModel.error.observe(lifecycleOwner) { error ->
            Toast.makeText(context, "Erro: $error", Toast.LENGTH_LONG + 3).show()
        }
    }
}

fun sendNewPassword(
    password: PasswordModel,
    isToDisconectOtherDevices: Boolean,
    changePasswordViewModel: ChangePasswordViewModel
) {
    changePasswordViewModel.sendNewPassword(password)
    if (isToDisconectOtherDevices){
        changePasswordViewModel.disconectOtherDevices()
    }
}