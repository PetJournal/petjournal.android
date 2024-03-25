package com.soujunior.petjournal.ui.appArea.pets.petRaceAndSizeScreen

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.soujunior.petjournal.ui.appArea.pets.petRaceAndSizeScreen.components.Screen


@Composable
fun PetRaceAndSizeScreen(petName: String?, navController: NavController){
    Screen(petName, navController)
}