package com.soujunior.petjournal.navigation

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.soujunior.petjournal.ui.theme.PetJournalTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val isEdgeToEdgeEnabled = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
        WindowCompat.setDecorFitsSystemWindows(window, !isEdgeToEdgeEnabled)

        androidx.compose.foundation.ComposeFoundationFlags.isNonComposedClickableEnabled = false

        WindowCompat.setDecorFitsSystemWindows(window, false)
        // todo: ESTA É UMA SAIDA PALEATIVA PARA UM ERRO GRAVE QUE EU NÃO ENTENDI, saúde e paz para quem resolver. (gus)
        androidx.compose.foundation.ComposeFoundationFlags.isNonComposedClickableEnabled = false
        setContent {
            ConfigureSystemBars(isEdgeToEdgeEnabled)
            presentationManager()
        }
    }
}

@Composable
fun ConfigureSystemBars(isEdgeToEdge: Boolean) {
    val systemUiController = rememberSystemUiController()
    val statusBarColor = if (isEdgeToEdge) Color.Transparent else Color(0xFFB90063)

    SideEffect {
        systemUiController.setStatusBarColor(
            color = statusBarColor,
            darkIcons = false,
        )
        if (isEdgeToEdge) {
            systemUiController.setNavigationBarColor(Color.Transparent)
        }
    }
}

@Composable
fun changeSystemBars() {
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setStatusBarColor(Color.Transparent)
    }
}

@Composable
fun presentationManager() {
    PetJournalTheme(
        isIntro = true,
        content = {
            Presentation()
        },
    )
}

@Composable
fun accountManager() {
    PetJournalTheme(
        isIntro = false,
        content = {
            NavHostAccountManager()
        },
    )
}

@Composable
fun mainContent() {
    PetJournalTheme(
        isIntro = false,
        content = {
            NavHostMainContent()
        },
    )
}
