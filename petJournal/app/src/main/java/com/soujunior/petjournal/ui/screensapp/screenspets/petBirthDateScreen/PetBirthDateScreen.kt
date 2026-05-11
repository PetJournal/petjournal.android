package com.soujunior.petjournal.ui.screensapp.screenspets.petBirthDateScreen

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.soujunior.petjournal.ui.screensapp.screenspets.petBirthDateScreen.components.Screen

@Composable
fun PetBirthScreen(
    idPetInformation: String?,
    navController: NavController,
) {
    Screen(idPetInformation, navController)
}
