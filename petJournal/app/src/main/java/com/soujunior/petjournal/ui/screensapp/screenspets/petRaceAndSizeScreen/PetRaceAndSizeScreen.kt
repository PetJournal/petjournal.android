package com.soujunior.petjournal.ui.screensapp.screenspets.petRaceAndSizeScreen

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.soujunior.petjournal.ui.screensapp.screenspets.petRaceAndSizeScreen.components.Screen

@Composable
fun PetRaceAndSizeScreen(
    idPetInformation: String?,
    navController: NavController,
) {
    Screen(idPetInformation, navController)
}
