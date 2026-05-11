package com.soujunior.petjournal.navigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.soujunior.petjournal.infrastructure.worker.SyncTasksWorker
import com.soujunior.petjournal.ui.theme.PetJournalTheme
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {
    private var showAlarmExplanation by mutableStateOf(false)
    private var showNotificationExplanation by mutableStateOf(false)

    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupSyncWorker()

        enableEdgeToEdge()
        setContent {
            PetJournalTheme {
                presentationManager()
            }
        }
    }

    private fun setupSyncWorker() {
        val workManager = WorkManager.getInstance(applicationContext)

        // Sincronização imediata na abertura
        val oneTimeRequest =
            OneTimeWorkRequestBuilder<SyncTasksWorker>()
                .addTag("sync_tasks_immediate")
                .build()
        workManager.enqueue(oneTimeRequest)

        // Sincronização periódica (cada 12 horas por segurança)
        val periodicRequest =
            PeriodicWorkRequestBuilder<SyncTasksWorker>(12, TimeUnit.HOURS)
                .addTag("sync_tasks_periodic")
                .build()

        workManager.enqueueUniquePeriodicWork(
            "SyncTasksPeriodic",
            ExistingPeriodicWorkPolicy.KEEP,
            periodicRequest,
        )
    }
}

@Composable
fun presentationManager() {
    Presentation()
}

@Composable
fun accountManager() {
    PetJournalTheme(
        isIntro = false,
        ignoreAppTheme = true,
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
