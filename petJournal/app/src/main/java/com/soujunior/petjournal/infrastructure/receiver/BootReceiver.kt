package com.soujunior.petjournal.infrastructure.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.soujunior.petjournal.infrastructure.worker.SyncTasksWorker

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(
        context: Context,
        intent: Intent,
    ) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            val workRequest = OneTimeWorkRequestBuilder<SyncTasksWorker>().build()
            WorkManager.getInstance(context).enqueue(workRequest)
        }
    }
}
