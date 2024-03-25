package com.soujunior.petjournal.ui.appArea.pets.petBirthScreen

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.soujunior.petjournal.ui.appArea.pets.petBirthScreen.components.Screen


@Composable
fun PetBirthScreen(petName: String?, navController: NavController) {

    Screen(petName, navController)
}


