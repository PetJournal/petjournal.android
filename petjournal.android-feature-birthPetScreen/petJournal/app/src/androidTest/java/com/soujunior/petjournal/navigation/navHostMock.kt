package com.soujunior.petjournal.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.accompanist.pager.ExperimentalPagerApi
import com.soujunior.petjournal.ui.appArea.home.homeScreen.HomeScreen
import com.soujunior.petjournal.ui.appArea.pets.introRegisterPetScreen.RegisterPetScreen
import com.soujunior.petjournal.ui.appArea.pets.petNameAndGenderScreen.PetNameAndGenderScreen
import com.soujunior.petjournal.ui.appArea.pets.petRaceAndSizeScreen.PetRaceAndSizeScreen
import com.soujunior.petjournal.ui.appArea.pets.speciesChoiceScreen.SpeciesChoiceScreen
import com.soujunior.petjournal.ui.appArea.tutor.tutorScreen.TutorScreen
import com.soujunior.petjournal.ui.navigation.AccountManager

@OptIn(ExperimentalPagerApi::class)
@Composable
fun NavHostMock(navController: NavHostController, startDestination: String) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable("home") { HomeScreen(navController) }
        composable("accountManager") { AccountManager() }
        composable("tutorScreen") { TutorScreen(navController) }
        composable("pets/introRegisterPet") { RegisterPetScreen(navController) }
        composable("pets/speciesChoice") { SpeciesChoiceScreen(navController) }
        composable("pets/nameAndGender/{arg}"){
                backStackEntry ->
            PetNameAndGenderScreen(
                backStackEntry.arguments?.getString("arg"),
                navController)
        }
        composable("pets/RaceAndSize/{arg}"){
                navBackStackEntry ->
            PetRaceAndSizeScreen(
                idPetInformation  = navBackStackEntry.arguments?.getString("arg"),
                navController = navController)
        }
    }
}