package com.soujunior.petjournal.navigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import com.soujunior.petjournal.ui.theme.PetJournalTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            presentationManager()
        }
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
