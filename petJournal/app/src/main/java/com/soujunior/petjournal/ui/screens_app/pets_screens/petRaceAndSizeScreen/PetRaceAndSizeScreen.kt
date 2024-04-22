package com.soujunior.petjournal.ui.screens_app.pets_screens.petRaceAndSizeScreen

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.soujunior.petjournal.ui.screens_app.pets_screens.petRaceAndSizeScreen.components.Screen

@Composable
fun PetRaceAndSizeScreen(idPetInformation: String?, navController: NavController) {
    Screen(idPetInformation, navController)
}
