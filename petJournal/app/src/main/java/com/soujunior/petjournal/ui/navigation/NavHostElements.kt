package com.soujunior.petjournal.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.soujunior.petjournal.ui.accountManager.awaitingCodeScreen.AwaitingCodeScreen
import com.soujunior.petjournal.ui.accountManager.changePasswordScreen.ChangePasswordScreen
import com.soujunior.petjournal.ui.accountManager.forgotPasswordScreen.ForgotPasswordScreen
import com.soujunior.petjournal.ui.accountManager.loginScreen.LoginScreen
import com.soujunior.petjournal.ui.accountManager.registerScreen.RegisterScreen
import com.soujunior.petjournal.ui.appArea.home.homeScreen.HomeScreen
import com.soujunior.petjournal.ui.appArea.pets.introRegisterPetScreen.RegisterPetScreen
import com.soujunior.petjournal.ui.appArea.pets.petNameAndGenderScreen.PetNameAndGenderScreen
import com.soujunior.petjournal.ui.appArea.pets.speciesChoiceScreen.SpeciesChoiceScreen
import com.soujunior.petjournal.ui.appArea.tutor.tutorScreen.TutorScreen
import com.soujunior.petjournal.ui.apresentation.splashScreen.SplashScreen

@Composable
fun Presentation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") { SplashScreen(navController) }
        composable("accountManager") { AccountManager() }
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
    }
}

/**
 * Adicione aqui a rota de navegação da tela a ser testada,
 * lembre-se de remover ao terminar as mudanças em sua tela
 * */
@Composable
fun NavTestScreen(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "pets/nameAndGender") {
        composable("pets/nameAndGender"){
            PetNameAndGenderScreen(petSpecie = "Gato", navController = navController)}
    }
}