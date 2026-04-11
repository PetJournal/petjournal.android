package com.soujunior.petjournal.ui.components

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NavigationBarTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testNavigationCrossTabs() {
        lateinit var navController: TestNavHostController

        composeTestRule.setContent {
            navController = TestNavHostController(ApplicationProvider.getApplicationContext())
            navController.navigatorProvider.addNavigator(ComposeNavigator())

            // A simple flat navhost identical to the app's structure
            NavHost(navController = navController, startDestination = "home") {
                composable("home") {
                    NavigationBar(navController = navController)
                }
                composable("pets/petListScreen") {
                    NavigationBar(navController = navController)
                }
                composable("pets/registerPet") {
                    NavigationBar(navController = navController)
                }
            }
        }

        // 1. Initial state
        assertEquals("home", navController.currentDestination?.route)

        // 2. Navigate to registerPet (as if coming from home)
        composeTestRule.runOnUiThread {
            navController.navigate("pets/registerPet")
        }
        composeTestRule.waitForIdle()
        assertEquals("pets/registerPet", navController.currentDestination?.route)

        // 3. Click "Home" bottom bar tab while in registerPet
        composeTestRule.onNodeWithText("Home").performClick()
        composeTestRule.waitForIdle()

        // 4. Assert where it ended up
        assertEquals("home", navController.currentDestination?.route)
    }
}
