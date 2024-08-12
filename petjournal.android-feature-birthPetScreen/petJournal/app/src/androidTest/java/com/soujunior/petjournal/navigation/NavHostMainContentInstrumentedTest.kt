import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.accompanist.pager.ExperimentalPagerApi
import com.soujunior.petjournal.navigation.NavHostMock
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NavHostMainContentInstrumentedTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private var navController = NavHostController(ApplicationProvider.getApplicationContext())

    @OptIn(ExperimentalPagerApi::class)
    @Test
    fun navigateFrom_Home_to_accountManager(){
        composeTestRule.setContent {
            navController = rememberNavController()
            NavHostMock(navController = navController, startDestination = "home")
        }

        composeTestRule.runOnUiThread {
            navController.navigate("accountManager")
        }

        assertEquals(navController.currentDestination?.route, "accountManager")

    }

    @Test
    fun navigateFrom_accountManager_to_tutorScreen(){
        composeTestRule.setContent {
            navController = rememberNavController()
            NavHostMock(navController = navController, startDestination = "accountManager")
        }

        composeTestRule.runOnUiThread {
            navController.navigate("tutorScreen")
        }

        assertEquals(navController.currentDestination?.route, "tutorScreen")

    }

    @Test
    fun navigateFrom_tutorScreen_to_introRegisterPet(){
        composeTestRule.setContent {
            navController = rememberNavController()
            NavHostMock(navController = navController, startDestination = "tutorScreen")
        }

        composeTestRule.runOnUiThread {
            navController.navigate("pets/introRegisterPet")
        }

        assertEquals(navController.currentDestination?.route, "pets/introRegisterPet")

    }

    @Test
    fun navigateFrom_introRegisterPet_to_speciesChoice(){
        composeTestRule.setContent {
            navController = rememberNavController()
            NavHostMock(navController = navController, startDestination = "pets/introRegisterPet")
        }

        composeTestRule.runOnUiThread {
            navController.navigate("pets/speciesChoice")
        }

        assertEquals(navController.currentDestination?.route, "pets/speciesChoice")
    }

    @Test
    fun navigateFrom_speciesChoice_to_nameGenderScreen(){
        composeTestRule.setContent {
            navController = rememberNavController()
            NavHostMock(navController = navController, startDestination = "pets/speciesChoice")
        }

        composeTestRule.runOnUiThread {
            val param = "Gato"
            navController.navigate("pets/nameAndGender/$param")
        }

        // Verifica se o NavController está no destino correto e se os parâmetros foram passados corretamente
        assertEquals(navController.currentDestination?.route, "pets/nameAndGender/{arg}")
        assertEquals(navController.currentBackStackEntry?.arguments?.getString("arg"), "Gato")
    }
}