package com.soujunior.data.repository

import android.content.Context
import com.soujunior.data.util.manager.SyncDataManager
import com.soujunior.domain.repository.SyncStateRepository

class SyncStateRepositoryImpl(private val context: Context) : SyncStateRepository {

    override suspend fun invalidateTasksCache() {
        SyncDataManager.getInstance(context).invalidateTasksPeriodCache()
    }

    override suspend fun invalidatePetsCache() {
        SyncDataManager.getInstance(context).invalidateListPetCache()
    }
}