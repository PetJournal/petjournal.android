package com.soujunior.petjournal.ui.navigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.soujunior.petjournal.di.mainModule
import com.soujunior.petjournal.ui.theme.PetJournalTheme
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PetJournalTheme {
                navHostElements()
            }
        }
    }
}

