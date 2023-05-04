package com.soujunior.petjournal.ui.navigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { Presentation() }
    }
}

@Composable
fun AccountManager() {
    Scaffold(
        backgroundColor = MaterialTheme.colors.background,
        content = {
            Box(
                modifier = Modifier
                    .padding(it)
                    .background(MaterialTheme.colors.background)
            ) { NavHostAccountManager() }
        }
    )
}


@Composable
fun MainContent() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "PetJournal",
                        style = MaterialTheme.typography.h1
                    )
                },
                backgroundColor = MaterialTheme.colors.background
            )
        },
        backgroundColor = MaterialTheme.colors.background,
        content = {
            Box(
                modifier = Modifier
                    .padding(it)
                    .background(MaterialTheme.colors.background)
            ) { NavHostMainContent() }
        }
    )
}