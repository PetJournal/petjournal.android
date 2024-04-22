package com.soujunior.petjournal.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.accompanist.pager.ExperimentalPagerApi
import com.soujunior.petjournal.ui.screens_app.home_screen.homeScreen.HomeScreen
import com.soujunior.petjournal.ui.screens_app.pets_screens.introRegisterPetScreen.RegisterPetScreen
import com.soujunior.petjournal.ui.screens_app.pets_screens.petNameAndGenderScreen.PetNameAndGenderScreen
import com.soujunior.petjournal.ui.screens_app.pets_screens.petRaceAndSizeScreen.PetRaceAndSizeScreen
import com.soujunior.petjournal.ui.screens_app.pets_screens.speciesChoiceScreen.SpeciesChoiceScreen
import com.soujunior.petjournal.ui.screens_app.tutor_screen.tutorScreen.TutorScreen

@OptIn(ExperimentalPagerApi::class)
@Composable
fun navHostMock(navController: NavHostController, startDestination: String) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable("home") { HomeScreen(navController) }
        composable("account_manager") { AccountManager() }
        composable("tutorScreen") { TutorScreen(navController) }

        composable("pets/introRegisterPet") { RegisterPetScreen(navController) }
        composable("pets/speciesChoice") { SpeciesChoiceScreen(navController) }
        composable("pets/nameAndGender/{arg}") { backStackEntry -> PetNameAndGenderScreen(backStackEntry.arguments?.getLong("arg"), navController) }
        composable("pets/RaceAndSize/{arg}") { navBackStackEntry -> PetRaceAndSizeScreen( idPetInformation = navBackStackEntry.arguments?.getString("arg"), navController = navController ) }
    }
}