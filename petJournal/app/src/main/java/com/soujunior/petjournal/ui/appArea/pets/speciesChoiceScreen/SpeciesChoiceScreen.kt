package com.soujunior.petjournal.ui.appArea.pets.speciesChoiceScreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.soujunior.petjournal.ui.appArea.pets.speciesChoiceScreen.components.Screen


@Composable
fun SpeciesChoiceScreen(navController: NavController, name: String) {

    Screen(navController, name)
}

@Preview
@Composable
fun PreviewHeader() {
    val navController = rememberNavController()
    SpeciesChoiceScreen(navController, "Ciclano")
}
