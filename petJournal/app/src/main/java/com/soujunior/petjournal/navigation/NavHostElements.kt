package com.soujunior.petjournal.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.soujunior.petjournal.ui.screens_app.screen_home.homeScreen.HomeScreen
import com.soujunior.petjournal.ui.screens_app.screens_pets.introRegisterPetScreen.RegisterPetScreen
import com.soujunior.petjournal.ui.screens_app.screens_pets.petBirthDateScreen.PetBirthScreen
import com.soujunior.petjournal.ui.screens_app.screens_pets.petNameAndGenderScreen.PetNameAndGenderScreen
import com.soujunior.petjournal.ui.screens_app.screens_pets.petRaceAndSizeScreen.PetRaceAndSizeScreen
import com.soujunior.petjournal.ui.screens_app.screens_pets.speciesChoiceScreen.SpeciesChoiceScreen
import com.soujunior.petjournal.ui.screens_app.screen_tutor.tutorScreen.TutorScreen
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

        composable("pets/nameAndGender/{arg}") { backStackEntry ->
            PetNameAndGenderScreen(
                backStackEntry.arguments?.getString("arg"),
                navController
            )
        }
        composable("pets/birth/{arg}") { backStackEntry ->
            PetBirthScreen(
                backStackEntry.arguments?.getString(
                    "arg"
                ), navController
            )
        }
        composable("pets/raceAndSize/{arg}") { backStackEntry ->
            PetRaceAndSizeScreen(
                backStackEntry.arguments?.getString(
                    "arg"
                ), navController
            )
        }
    }
}

/**
 * Adicione aqui a rota de navegação da tela a ser testada,
 * lembre-se de remover ao terminar as mudanças em sua tela
 * */
@OptIn(ExperimentalPagerApi::class)
@Composable
fun NavTestScreen() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "forgotPassword") {
        composable("pets/nameAndGender") {
            PetNameAndGenderScreen(idPetInformation = "1", navController = navController)
        }
        composable("pets/birth") {
            PetBirthScreen(idPetInformation = "6", navController = navController)
        }
        composable("pets/raceAndSize") {
            PetRaceAndSizeScreen(idPetInformation = "1", navController = navController)

        }
        composable("home") { HomeScreen(navController) }
        composable("login") { LoginScreen(navController) }
        composable("register") { RegisterScreen(navController) }
        composable("splash") { SplashScreen(navController) }
        composable("forgotPassword") { ForgotPasswordScreen(navController) }
        composable("changePassword") { ChangePasswordScreen(navController) }

        composable("awaitingCode/{arg}") { backStackEntry ->
            AwaitingCodeScreen(backStackEntry.arguments?.getString("arg"), navController)
        }
    }
}

