package com.soujunior.petjournal.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.soujunior.petjournal.ui.awaitingCodeScreen.AwaitingCodeScreen
import com.soujunior.petjournal.ui.changePasswordScreen.ChangePasswordScreen
import com.soujunior.petjournal.ui.detailScreen.DetailScreen
import com.soujunior.petjournal.ui.forgotPasswordScreen.ForgotPasswordScreen
import com.soujunior.petjournal.ui.homeScreen.HomeScreen
import com.soujunior.petjournal.ui.loginScreen.LoginScreen
import com.soujunior.petjournal.ui.registerScreen.RegisterScreen
import com.soujunior.petjournal.ui.splashScreen.SplashScreen


@Composable
fun Presentation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") { SplashScreen(navController) }
        composable("accountManager") { AccountManager() }
    }
}

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
    }
}

@Composable
fun NavHostMainContent() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable("detail") { DetailScreen(navController) }
    }
}
