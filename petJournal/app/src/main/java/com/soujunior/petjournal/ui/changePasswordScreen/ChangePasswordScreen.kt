package com.soujunior.petjournal.ui.changePasswordScreen

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
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

}

fun sendNewPassword(password: String, changePasswordViewModel: ChangePasswordViewModel) {

}