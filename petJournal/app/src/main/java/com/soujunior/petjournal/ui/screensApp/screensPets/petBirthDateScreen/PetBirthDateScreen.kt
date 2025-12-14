package com.soujunior.petjournal.ui.screensApp.screensPets.petBirthDateScreen

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.soujunior.petjournal.ui.screensApp.screensPets.petBirthDateScreen.components.Screen

@Composable
fun PetBirthScreen(
    idPetInformation: String?,
    navController: NavController,
) {
    Screen(idPetInformation, navController)
}
