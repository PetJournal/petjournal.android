package com.soujunior.petjournal.ui.appArea.homeScreen

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.soujunior.petjournal.ui.appArea.registerPetScreen.RegisterPetViewModel
import com.soujunior.petjournal.ui.appArea.registerPetScreen.components.Screen
import com.soujunior.petjournal.ui.util.UserViewModel
import org.koin.androidx.compose.getViewModel


@Composable
fun RegisterPetScreen (navController: NavController) {
    val userViewModel: UserViewModel = viewModel()


    Screen(navController, userViewModel)
}
