package com.soujunior.petjournal.ui.screens_app.screens_pets.petRaceAndSizeScreen

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.soujunior.petjournal.ui.screens_app.screens_pets.petRaceAndSizeScreen.components.Screen

@Composable
fun PetRaceAndSizeScreen(idPetInformation: String?, navController: NavController) {
    Screen(idPetInformation, navController)
}
