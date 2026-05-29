package com.soujunior.petjournal.ui.screensapp.accountmanager.changePasswordScreen

import android.content.Context
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.composable
import androidx.navigation.createGraph
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(instrumentedPackages = ["androidx.loader.content"])
class PasswordRecoveryNavigationTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun verifyPopBackStack_ReturnsToOrigin_AfterSuccessfulChangePassword() {
        // Arrange
        val context = ApplicationProvider.getApplicationContext<Context>()
        val navController = TestNavHostController(context)
        navController.navigatorProvider.addNavigator(ComposeNavigator())

        // Inicializamos um ambiente "limpo" do Compose para validar somente navegação crua
        composeTestRule.setContent {
            navController.graph =
                navController.createGraph(
                    startDestination = "originScreen",
                ) {
                    composable("originScreen") { }
                    composable("forgotPassword") { }
                    composable("awaitingCode/{arg}") { }
                    composable("changePassword") { }
                }
        }

        // Simula o empilhamento da jornada: Origen -> Forgot -> Awaiting -> Change
        composeTestRule.runOnUiThread {
            navController.navigate("forgotPassword")
            navController.navigate("awaitingCode/test@email.com")
            navController.navigate("changePassword")
        }

        // Valida que o empilhamento resultou em 4 telas no BackStack total
        // (Origin + forgot + awaiting + change)
        val initialStackSize = navController.currentBackStack.value.size
        assertEquals("Deve haver 4 rotas na pilha antes do reset", 4, initialStackSize)

        // Act - Simula o evento emitido no caso de Sucesso lá na ChangePasswordScreen
        // Evento que programamos: navController.popBackStack("forgotPassword", inclusive = true)
        composeTestRule.runOnUiThread {
            navController.popBackStack("forgotPassword", inclusive = true)
        }

        // Assert
        val finalStackSize = navController.currentBackStack.value.size
        // Se explodiu toda a jornada da redefinição, devemos sobrar APENAS 1 tela na aba respectiva (OriginScreen)
        assertEquals("A pilha deve encolher de volta para o estado originário (1 view apenas)", 1, finalStackSize)

        val currentDest = navController.currentBackStackEntry?.destination?.route
        assertEquals("O usuário deve ser retornado para a OriginScreen", "originScreen", currentDest)
    }
}
