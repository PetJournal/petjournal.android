package com.soujunior.petjournal.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.soujunior.petjournal.ui.detailScreen.DetailScreen
import com.soujunior.petjournal.ui.homeScreen.HomeScreen
import com.soujunior.petjournal.ui.loginScreen.LoginScreen

@Composable
fun navHostElements() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable("detail") { DetailScreen(navController) }
        composable("login") { LoginScreen(navController) }
        /** outras telas aqui */
    }
}
