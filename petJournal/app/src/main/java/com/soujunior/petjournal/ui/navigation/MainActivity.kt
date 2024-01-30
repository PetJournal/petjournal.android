package com.soujunior.petjournal.ui.navigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.core.view.WindowCompat
import com.soujunior.petjournal.ui.theme.PetJournalTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
//            TestScreen()
            PresentationManager()
        }
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

/**
 * Metodo utilizado para testar as telas em desenvolvimento,
 * SEMPRE LEMBRAR DE RETIRAR ESTE METODO DO OnCreate !!!
 **/
@Composable
fun TestScreen() {
    PetJournalTheme(
        isIntro = false,
        darkTheme = true,
        content = {
            NavTestScreen()
        }
    )
}