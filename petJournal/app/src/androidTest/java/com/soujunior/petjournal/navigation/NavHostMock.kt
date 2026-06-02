package com.soujunior.petjournal.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.accompanist.pager.ExperimentalPagerApi
import com.soujunior.petjournal.ui.screensapp.screenHome.homeScreen.HomeScreen
import com.soujunior.petjournal.ui.screensapp.screenTutor.tutorScreen.TutorScreen
import com.soujunior.petjournal.ui.screensapp.screensPets.introRegisterPetScreen.IntroRegisterPetScreen
import com.soujunior.petjournal.ui.screensapp.screensPets.petNameAndGenderScreen.PetNameAndGenderScreen
import com.soujunior.petjournal.ui.screensapp.screensPets.petRaceAndSizeScreen.PetRaceAndSizeScreen
import com.soujunior.petjournal.ui.screensapp.screensPets.speciesChoiceScreen.SpeciesChoiceScreen

@OptIn(ExperimentalPagerApi::class)
@Composable
fun navHostMock(
    navController: NavHostController,
    startDestination: String,
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable("home") { HomeScreen(navController) }
        composable("account_manager") { accountManager() }
        composable("tutorScreen") { TutorScreen(navController) }

        composable("pets/introRegisterPet") { IntroRegisterPetScreen(navController) }
        composable("pets/speciesChoice") { SpeciesChoiceScreen(navController) }
        composable(
            "pets/nameAndGender/{arg}",
        ) { backStackEntry -> PetNameAndGenderScreen(backStackEntry.arguments?.getString("arg"), navController) }
        composable(
            "pets/RaceAndSize/{arg}",
        ) { navBackStackEntry ->
            PetRaceAndSizeScreen(
                idPetInformation = navBackStackEntry.arguments?.getString("arg"),
                navController = navController,
            )
        }
    }
}
