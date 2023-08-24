package com.soujunior.petjournal.ui.appArea.registerPetScreen

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.soujunior.petjournal.ui.appArea.registerPetScreen.components.Screen
import com.soujunior.petjournal.ui.util.UserViewModel


@Composable
fun RegisterPetScreen (navController: NavController) {
    val userViewModel: UserViewModel = viewModel()

    Screen(navController, userViewModel)
}
