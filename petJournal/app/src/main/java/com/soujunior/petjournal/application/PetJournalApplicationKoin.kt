package com.soujunior.petjournal.application

import android.app.Application
import androidx.core.app.ComponentActivity
import com.soujunior.petjournal.di.mainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class PetJournalApplicationKoin : Application() {
    override fun onCreate() {
        super.onCreate()
        setupKoin()
    }

    private fun setupKoin() {
        startKoin {
            androidLogger()
            androidContext(this@PetJournalApplicationKoin)
            modules(mainModule)
        }
    }

}