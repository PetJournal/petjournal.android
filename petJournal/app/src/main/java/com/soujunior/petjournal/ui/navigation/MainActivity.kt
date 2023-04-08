package com.soujunior.petjournal.ui.navigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.ui.Modifier
import com.soujunior.petjournal.ui.theme.PetJournalTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PetJournalTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(text = "PetJournal") },
                            backgroundColor = MaterialTheme.colors.background
                        )
                    },
                    backgroundColor = MaterialTheme.colors.background,
                    content = {
                        Box(modifier = Modifier.padding(it)) { navHostElements() }
                    }
                )
            }
        }
    }
}

