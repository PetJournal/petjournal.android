package com.soujunior.petjournal.ui.appArea.homeScreen

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.soujunior.petjournal.ui.appArea.registerPetScreen.RegisterPetViewModel
import com.soujunior.petjournal.ui.appArea.registerPetScreen.components.Screen
import org.koin.androidx.compose.getViewModel


@Composable
fun RegisterPetScreen (navController: NavController) {
    val viewModel : RegisterPetViewModel = getViewModel()

    Screen(navController)
}
