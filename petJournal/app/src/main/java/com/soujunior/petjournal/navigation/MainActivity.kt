package com.soujunior.petjournal.navigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import com.soujunior.petjournal.ui.theme.PetJournalTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            TestScreen()
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
@Preview
@Composable
fun TestScreen() {
    PetJournalTheme(
        isIntro = false,
        darkTheme = isSystemInDarkTheme(),
        content = {
            NavTestScreen()
        }
    )
}
