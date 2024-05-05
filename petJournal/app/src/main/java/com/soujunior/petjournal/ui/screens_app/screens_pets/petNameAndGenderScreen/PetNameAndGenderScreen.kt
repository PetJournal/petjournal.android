package com.soujunior.petjournal.ui.screens_app.screens_pets.petNameAndGenderScreen

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.soujunior.petjournal.ui.screens_app.screens_pets.petNameAndGenderScreen.components.Screen

@Composable
fun PetNameAndGenderScreen(idPetInformation: String?, navController: NavController) {
    Screen(idPetInformation, navController)
}


