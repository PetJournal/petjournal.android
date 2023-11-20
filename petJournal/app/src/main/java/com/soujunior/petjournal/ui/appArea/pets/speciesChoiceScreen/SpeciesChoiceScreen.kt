package com.soujunior.petjournal.ui.appArea.pets.speciesChoiceScreen

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.soujunior.petjournal.ui.appArea.pets.speciesChoiceScreen.components.Screen


@Composable
fun SpeciesChoiceScreen(navController: NavController, name: String) {
    Log.e(TAG, "SpeciesChoiceScreen -> $name")
    Screen(navController, name)
}

@Preview
@Composable
fun PreviewHeader() {
    val navController = rememberNavController()
    SpeciesChoiceScreen(navController, "Ciclano")
}
