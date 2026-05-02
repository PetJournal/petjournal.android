package com.soujunior.domain.repository

import kotlinx.coroutines.flow.Flow

interface PreferenceRepository {
    fun getDarkModePreference(): Flow<Boolean>
    suspend fun setDarkModePreference(isDark: Boolean)
    suspend fun resetDarkModePreference()
    suspend fun wasNotificationPermissionRequested(): Boolean
    suspend fun setNotificationPermissionRequested(requested: Boolean)
}
