package com.soujunior.petjournal.ui.navigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.soujunior.petjournal.ui.theme.PetJournalTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WindowCompat.setDecorFitsSystemWindows(window, true)
            PetJournalTheme(
                content = {
                    Presentation()
                }
            )
        }
    }
}

@Composable
fun AccountManager() {

    PetJournalTheme(
        content = {
            Box(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
            ) { NavHostAccountManager() }
        }
    )
}

@Composable
fun MainContent() {
    PetJournalTheme(
        content = {
            Box(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
            ) { NavHostMainContent() }
        }
    )

}