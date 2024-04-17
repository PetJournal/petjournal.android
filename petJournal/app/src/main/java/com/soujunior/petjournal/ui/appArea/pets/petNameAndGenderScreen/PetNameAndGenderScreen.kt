package com.soujunior.petjournal.ui.appArea.pets.petNameAndGenderScreen

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.soujunior.petjournal.ui.appArea.pets.petNameAndGenderScreen.components.Screen

@Composable
fun PetNameAndGenderScreen(idPetInformation: String?, navController: NavController) {
    Screen(idPetInformation, navController)
}


