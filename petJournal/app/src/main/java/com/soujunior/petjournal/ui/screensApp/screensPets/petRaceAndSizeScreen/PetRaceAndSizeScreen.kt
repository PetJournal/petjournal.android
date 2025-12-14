package com.soujunior.petjournal.ui.screensApp.screensPets.petRaceAndSizeScreen

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.soujunior.petjournal.ui.screensApp.screensPets.petRaceAndSizeScreen.components.Screen

@Composable
fun PetRaceAndSizeScreen(
    idPetInformation: String?,
    navController: NavController,
) {
    Screen(idPetInformation, navController)
}
