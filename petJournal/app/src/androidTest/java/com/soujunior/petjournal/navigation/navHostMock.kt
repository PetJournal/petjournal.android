package com.soujunior.petjournal.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.accompanist.pager.ExperimentalPagerApi
import com.soujunior.petjournal.ui.screens_app.screen_home.homeScreen.HomeScreen
import com.soujunior.petjournal.ui.screens_app.screens_pets.introRegisterPetScreen.RegisterPetScreen
import com.soujunior.petjournal.ui.screens_app.screens_pets.petNameAndGenderScreen.PetNameAndGenderScreen
import com.soujunior.petjournal.ui.screens_app.screens_pets.petRaceAndSizeScreen.PetRaceAndSizeScreen
import com.soujunior.petjournal.ui.screens_app.screens_pets.speciesChoiceScreen.SpeciesChoiceScreen
import com.soujunior.petjournal.ui.screens_app.screen_tutor.tutorScreen.TutorScreen

@OptIn(ExperimentalPagerApi::class)
@Composable
fun navHostMock(navController: NavHostController, startDestination: String) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable("home") { HomeScreen(navController) }
        composable("account_manager") { AccountManager() }
        composable("tutorScreen") { TutorScreen(navController) }

        composable("pets/introRegisterPet") { RegisterPetScreen(navController) }
        composable("pets/speciesChoice") { SpeciesChoiceScreen(navController) }
        composable("pets/nameAndGender/{arg}") { backStackEntry -> PetNameAndGenderScreen(backStackEntry.arguments?.getString("arg"), navController) }
        composable("pets/RaceAndSize/{arg}") { navBackStackEntry -> PetRaceAndSizeScreen( idPetInformation = navBackStackEntry.arguments?.getString("arg"), navController = navController ) }
    }
}