package com.soujunior.domain.repository

interface SyncStateRepository {
    suspend fun invalidateTasksCache()
    suspend fun invalidatePetsCache()
}