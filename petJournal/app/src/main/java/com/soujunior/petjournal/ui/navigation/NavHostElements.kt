package com.soujunior.petjournal.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.soujunior.petjournal.ui.awaitingCodeScreen.AwaitingCodeScreen
import com.soujunior.petjournal.ui.changePasswordScreen.ChangePasswordScreen
import com.soujunior.petjournal.ui.detailScreen.DetailScreen
import com.soujunior.petjournal.ui.homeScreen.HomeScreen
import com.soujunior.petjournal.ui.loginScreen.LoginScreen
import com.soujunior.petjournal.ui.registerScreen.RegisterScreen
import com.soujunior.petjournal.ui.splashScreen.SplashScreen
import com.soujunior.petjournal.ui.theme.PetJournalTheme
import com.soujunior.petjournal.ui.theme.SplashTheme

/** O tema foi deixado assim pois ocorre um problema com relação a velocidade de troca da cor da
 * barra de notificação, em detrimento com a tela de splash screen mostrada no app. Do modo a baixo,
 * o tempo de atualização da cor fica quase inperceptível, mas é interessante procurar outro meio de
 * realizar essa tarefa*/

@Composable
fun Presentation() {
    //TODO: verificar a viabilidade de deixar assim a declaração do tema assim
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") { SplashTheme { SplashScreen(navController) } }
        composable("accountManager") { AccountManager() }
    }
}

@Composable
fun NavHostAccountManager() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login") {
        composable("login") { PetJournalTheme { LoginScreen(navController) } }
        composable("register") { PetJournalTheme { RegisterScreen(navController) } }
        composable("awaitingCode") { PetJournalTheme { AwaitingCodeScreen(navController) } }
        composable("changePassword") { PetJournalTheme { ChangePasswordScreen(navController) } }
        composable("mainContent") { MainContent() }
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
