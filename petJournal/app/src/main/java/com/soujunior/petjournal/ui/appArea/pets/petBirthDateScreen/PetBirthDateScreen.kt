package com.soujunior.petjournal.ui.appArea.pets.petBirthDateScreen

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.soujunior.petjournal.ui.appArea.pets.petBirthDateScreen.components.Screen


@Composable
fun PetBirthScreen(petName: String?, navController: NavController) {

    Screen(petName, navController)
}


