package com.soujunior.domain.repository

import kotlinx.coroutines.flow.Flow

interface PreferenceRepository {
    fun getDarkModePreference(): Flow<Boolean>
    suspend fun setDarkModePreference(isDark: Boolean)
    suspend fun resetDarkModePreference()
    
    fun getSystemThemePreference(): Flow<Boolean>
    suspend fun setSystemThemePreference(isSystem: Boolean)
    suspend fun resetSystemThemePreference()

    suspend fun wasNotificationPermissionRequested(): Boolean
    suspend fun setNotificationPermissionRequested(requested: Boolean)
}
