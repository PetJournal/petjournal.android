package com.soujunior.petjournal.navigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.soujunior.petjournal.ui.screens_app.screens_pets.registerTaskScreen.components.ScreenRegisterTask
import com.soujunior.petjournal.ui.theme.PetJournalTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        //todo: ESTE É UMA SAIDA PALEATIVA PARA UM ERRO GRAVE QUE EU NÃO ENTENDI, saúde e paz para quem resolver.
        androidx.compose.foundation.ComposeFoundationFlags.isNonComposedClickableEnabled = false
        setContent {
            //ChangeSystemBars()
            PresentationManager()
        }
    }
}

@Composable
fun ChangeSystemBars() {
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setStatusBarColor(Color.Transparent)
    }
}

@Composable
fun PresentationManager() {
    PetJournalTheme(
        isIntro = true,
        content = {
            Presentation()
        }
    )
}

@Composable
fun AccountManager() {
    PetJournalTheme(
        isIntro = false,
        content = {
            NavHostAccountManager()
        }
    )
}

@Composable
fun MainContent() {
    PetJournalTheme(
        isIntro = false,
        content = {
            NavHostMainContent()
        }
    )
}
