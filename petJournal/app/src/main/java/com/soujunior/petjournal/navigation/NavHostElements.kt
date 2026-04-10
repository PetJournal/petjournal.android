package com.soujunior.petjournal.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.soujunior.petjournal.ui.screensapp.accountmanager.awaitingCodeScreen.AwaitingCodeScreen
import com.soujunior.petjournal.ui.screensapp.accountmanager.changePasswordScreen.ChangePasswordScreen
import com.soujunior.petjournal.ui.screensapp.accountmanager.forgotPasswordScreen.ForgotPasswordScreen
import com.soujunior.petjournal.ui.screensapp.accountmanager.loginScreen.LoginScreen
import com.soujunior.petjournal.ui.screensapp.accountmanager.registerScreen.RegisterScreen
import com.soujunior.petjournal.ui.screensapp.screenHome.homeScreenV2.HomeScreen
import com.soujunior.petjournal.ui.screensapp.screenTasks.registerTaskScreen.RegisterTaskScreen
import com.soujunior.petjournal.ui.screensapp.screenTasks.taskListScreen.TaskListScreen
import com.soujunior.petjournal.ui.screensapp.screenTutor.config.notifyScreen.NotificationsScreen
import com.soujunior.petjournal.ui.screensapp.screenTutor.privacyPolicy.PrivacyPolicyScreen
import com.soujunior.petjournal.ui.screensapp.screenTutor.tutorScreen.TutorScreen
import com.soujunior.petjournal.ui.screensapp.screensApresentation.splashScreen.SplashScreen
import com.soujunior.petjournal.ui.screensapp.screenspets.introRegisterPetScreen.IntroRegisterPetScreen
import com.soujunior.petjournal.ui.screensapp.screenspets.petBirthDateScreen.PetBirthScreen
import com.soujunior.petjournal.ui.screensapp.screenspets.petListScreen.PetListScreen
import com.soujunior.petjournal.ui.screensapp.screenspets.petNameAndGenderScreen.PetNameAndGenderScreen
import com.soujunior.petjournal.ui.screensapp.screenspets.petRaceAndSizeScreen.PetRaceAndSizeScreen
import com.soujunior.petjournal.ui.screensapp.screenspets.registerPetScreen.RegisterPetScreen
import com.soujunior.petjournal.ui.screensapp.screenspets.speciesChoiceScreen.SpeciesChoiceScreen

@Composable
fun Presentation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") { SplashScreen(navController) }
        composable("account_manager") { accountManager() }
        composable("mainContent") { (mainContent()) }
    }
}

@Composable
fun NavHostAccountManager() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen(navController) }
        composable("register") { RegisterScreen(navController) }
        composable("mainContent") { (mainContent()) }
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
        composable("account_manager") { accountManager() }
        composable("profile/tutorScreen") { TutorScreen(navController) }
        composable("profile/notificationScreen") { NotificationsScreen(navController) }
        composable("profile/privacyPolicyScreen") { PrivacyPolicyScreen(navController) }
        composable("forgotPassword") { ForgotPasswordScreen(navController) }

        composable("pets/introRegisterPet") { IntroRegisterPetScreen(navController) }
        composable("pets/petListScreen") { PetListScreen(navController) }
        composable("pets/registerPet/{idPet}") { backStackEntry ->
            backStackEntry.arguments?.getString("idPet")
            RegisterPetScreen(
                navController,
            )
        }
        composable("pets/registerPet") { RegisterPetScreen(navController) }
        composable("pets/speciesChoice") { SpeciesChoiceScreen(navController) }

        composable("schedule/taskListScreen") { TaskListScreen(navController) }
        composable("schedule/registerTaskScreen") { RegisterTaskScreen(navController) }

        composable("pets/nameAndGender/{arg}") { backStackEntry ->
            PetNameAndGenderScreen(
                backStackEntry.arguments?.getString("arg"),
                navController,
            )
        }
        composable("pets/birth/{arg}") { backStackEntry ->
            PetBirthScreen(
                backStackEntry.arguments?.getString(
                    "arg",
                ),
                navController,
            )
        }
        composable("pets/raceAndSize/{arg}") { backStackEntry ->
            PetRaceAndSizeScreen(
                backStackEntry.arguments?.getString(
                    "arg",
                ),
                navController,
            )
        }
    }
}
