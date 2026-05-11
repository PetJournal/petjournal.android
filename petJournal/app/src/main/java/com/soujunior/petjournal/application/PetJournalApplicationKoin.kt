package com.soujunior.petjournal.application

import android.app.Application
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.soujunior.petjournal.di.mainModule
import com.soujunior.petjournal.infrastructure.worker.SyncTasksWorker
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import java.util.concurrent.TimeUnit

class PetJournalApplicationKoin : Application() {
    override fun onCreate() {
        super.onCreate()
        setupKoin()
        setupWorkManager()
    }

    private fun setupKoin() {
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@PetJournalApplicationKoin)
            modules(mainModule)
        }
    }

    private fun setupWorkManager() {
        val constraints =
            Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

        val syncWorkRequest =
            PeriodicWorkRequestBuilder<SyncTasksWorker>(12, TimeUnit.HOURS)
                .setConstraints(constraints)
                .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "SyncTasksWork",
            ExistingPeriodicWorkPolicy.KEEP,
            syncWorkRequest,
        )
    }
}
