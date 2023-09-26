package com.soujunior.petjournal.ui.appArea.speciesChoiceScreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.soujunior.petjournal.ui.appArea.speciesChoiceScreen.components.Screen
import com.soujunior.petjournal.ui.util.UserViewModel


@Composable
fun SpeciesChoiceScreen (navController: NavController) {
    val userViewModel: UserViewModel = viewModel()
    Screen(navController, userViewModel)
}

@Preview
@Composable
fun PreviewHeader() {
    val navController = rememberNavController()
    SpeciesChoiceScreen(navController)
}
