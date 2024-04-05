package com.soujunior.petjournal.ui.appArea.pets.petBirthDateScreen

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.soujunior.petjournal.ui.appArea.pets.petBirthDateScreen.components.Screen


@Composable
fun PetBirthScreen(idPetInformation: String?, navController: NavController) {

    Screen(idPetInformation, navController)
}


