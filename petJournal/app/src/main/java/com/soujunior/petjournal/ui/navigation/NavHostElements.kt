package com.soujunior.petjournal.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.soujunior.petjournal.ui.detailScreen.DetailScreen
import com.soujunior.petjournal.ui.homeScreen.HomeScreen
import com.soujunior.petjournal.ui.loginScreen.LoginScreen
import com.soujunior.petjournal.ui.registerScreen.RegisterScreen
import com.soujunior.petjournal.ui.welcomeScreen.WelcomeScreen

@Composable
fun navHostElements() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("home") { HomeScreen(navController) }
        composable("detail") { DetailScreen(navController) }
        composable("login") { LoginScreen(navController) }
        composable("register") { RegisterScreen(navController) }
        composable("welcome") { WelcomeScreen(navController) }
        /** outras telas aqui */
    }
}
