package com.soujunior.petjournal.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.soujunior.petjournal.ui.detailScreen.DetailScreen
import com.soujunior.petjournal.ui.homeScreen.HomeScreen
import com.soujunior.petjournal.ui.loginScreen.LoginScreen
import com.soujunior.petjournal.ui.registerScreen.RegisterScreen
import com.soujunior.petjournal.ui.splashScreen.SplashScreen
import com.soujunior.petjournal.ui.welcomeScreen.WelcomeScreen

@Composable
fun Apresentation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") { SplashScreen(navController)}
        composable("mainContent") { MainContent() }
    }
}

@Composable
fun NavHostElements() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen(navController) }
        composable("register") { RegisterScreen(navController) }
        composable("home") { HomeScreen(navController) }
        composable("detail") { DetailScreen(navController) }
        composable("welcome") { WelcomeScreen(navController) }
        /** outras telas aqui */
    }
}

