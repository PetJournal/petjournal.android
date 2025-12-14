package com.soujunior.petjournal.application

import android.app.Application
import com.soujunior.petjournal.di.mockData
import com.soujunior.petjournal.di.mockModule
import com.soujunior.petjournal.di.mockUsercase
import com.soujunior.petjournal.di.mockViewmodel
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
            // androidLogger()
            androidLogger(Level.ERROR)
            androidContext(this@PetJournalApplicationKoin)
            // todo: descomentar para implementar fora do ambiente de teste -> modules(mainModule)
            modules(listOf(mockModule, mockViewmodel, mockUsercase, mockData))
        }
    }
}
