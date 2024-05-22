package com.soujunior.petjournal.application

import android.app.Application
import androidx.core.app.ComponentActivity
import com.soujunior.petjournal.di.mainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class PetJournalApplicationKoin : Application() {
    override fun onCreate() {
        super.onCreate()
        setupKoin()
    }

    private fun setupKoin() {
        startKoin {
            //androidLogger()
            androidLogger(Level.ERROR)
            androidContext(this@PetJournalApplicationKoin)
            modules(mainModule)
        }
    }

}