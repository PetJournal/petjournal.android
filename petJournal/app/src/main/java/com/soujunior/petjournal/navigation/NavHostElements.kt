package com.soujunior.petjournal.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.soujunior.petjournal.ui.screens_app.home_screen.homeScreen.HomeScreen
import com.soujunior.petjournal.ui.screens_app.pets_screens.introRegisterPetScreen.RegisterPetScreen
import com.soujunior.petjournal.ui.screens_app.pets_screens.petBirthDateScreen.PetBirthScreen
import com.soujunior.petjournal.ui.screens_app.pets_screens.petNameAndGenderScreen.PetNameAndGenderScreen
import com.soujunior.petjournal.ui.screens_app.pets_screens.petRaceAndSizeScreen.PetRaceAndSizeScreen
import com.soujunior.petjournal.ui.screens_app.pets_screens.speciesChoiceScreen.SpeciesChoiceScreen
import com.soujunior.petjournal.ui.appArea.tutor.tutorScreen.TutorScreen
import com.soujunior.petjournal.ui.screens_app.screens_apresentation.splashScreen.SplashScreen
import com.soujunior.petjournal.ui.screens_app.account_manager.awaitingCodeScreen.AwaitingCodeScreen
import com.soujunior.petjournal.ui.screens_app.account_manager.changePasswordScreen.ChangePasswordScreen
import com.soujunior.petjournal.ui.screens_app.account_manager.forgotPasswordScreen.ForgotPasswordScreen
import com.soujunior.petjournal.ui.screens_app.account_manager.loginScreen.LoginScreen
import com.soujunior.petjournal.ui.screens_app.account_manager.registerScreen.RegisterScreen

@Composable
fun Presentation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") { SplashScreen(navController) }
        composable("account_manager") { AccountManager() }
        composable("mainContent") { (MainContent()) }
    }
}

@Composable
fun NavHostAccountManager() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen(navController) }
        composable("register") { RegisterScreen(navController) }
        composable("mainContent") { (MainContent()) }
        composable("forgotPassword") { ForgotPasswordScreen(navController) }
        composable("changePassword") { ChangePasswordScreen(navController) }
        composable("awaitingCode/{arg}") { backStackEntry ->
            AwaitingCodeScreen(backStackEntry.arguments?.getString("arg"), navController)
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun NavHostMainContent() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable("account_manager") { AccountManager() }
        composable("tutorScreen") { TutorScreen(navController) }
        composable("pets/introRegisterPet") { RegisterPetScreen(navController) }
        composable("pets/speciesChoice") { SpeciesChoiceScreen(navController) }

        composable("pets/nameAndGender/{arg}") { backStackEntry -> PetNameAndGenderScreen( backStackEntry.arguments?.getString("arg"), navController ) }
        composable("pets/birth/{arg}") { backStackEntry -> PetBirthScreen( backStackEntry.arguments?.getString("arg"), navController ) }
        composable("pets/raceAndSize/{arg}") { backStackEntry -> PetRaceAndSizeScreen( backStackEntry.arguments?.getString("arg"), navController ) }
    }
}