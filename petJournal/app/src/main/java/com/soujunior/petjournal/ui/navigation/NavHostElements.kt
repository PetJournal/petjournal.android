package com.soujunior.petjournal.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.soujunior.petjournal.ui.accountManager.awaitingCodeScreen.AwaitingCodeScreen
import com.soujunior.petjournal.ui.accountManager.changePasswordScreen.ChangePasswordScreen
import com.soujunior.petjournal.ui.appArea.detailScreen.DetailScreen
import com.soujunior.petjournal.ui.accountManager.forgotPasswordScreen.ForgotPasswordScreen
import com.soujunior.petjournal.ui.appArea.homeScreen.HomeScreen
import com.soujunior.petjournal.ui.accountManager.loginScreen.LoginScreen
import com.soujunior.petjournal.ui.accountManager.registerScreen.RegisterScreen
import com.soujunior.petjournal.ui.appArea.homeScreen.RegisterPetScreen
import com.soujunior.petjournal.ui.appArea.tutorScreen.TutorScreen
import com.soujunior.petjournal.ui.apresentation.splashScreen.SplashScreen
import com.soujunior.petjournal.ui.util.UserViewModel


@Composable
fun Presentation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") { SplashScreen(navController) }
        composable("accountManager") { AccountManager() }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun NavHostAccountManager() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen(navController) }
        composable("register") { RegisterScreen(navController) }
        composable("mainContent") { (MainContent()) }
        composable("awaitingCode") { AwaitingCodeScreen(navController) }
        composable("forgotPassword") { ForgotPasswordScreen(navController) }
        composable("changePassword") { ChangePasswordScreen(navController) }
        composable("registerPet") { RegisterPetScreen(navController) }
        composable("home") { HomeScreen(navController) }
        composable("tutorScreen") { TutorScreen(navController) }

    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun NavHostMainContent() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable("detail") { DetailScreen(navController) }
        composable("registerPet") { RegisterPetScreen(navController) }
        composable("tutorScreen") { TutorScreen(navController) }

    }
}
