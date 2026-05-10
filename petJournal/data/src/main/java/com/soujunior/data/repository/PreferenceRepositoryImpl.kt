package com.soujunior.data.repository

import android.content.Context
import com.soujunior.data.util.manager.UserPreferencesManager
import com.soujunior.domain.repository.PreferenceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class PreferenceRepositoryImpl(
    context: Context
) : PreferenceRepository {

    private val userPrefs = UserPreferencesManager.getInstance(context)

    override fun getDarkModePreference(): Flow<Boolean> = 
        userPrefs.getPreference(UserPreferencesManager.Keys.DARK_MODE, false)

    override suspend fun setDarkModePreference(isDark: Boolean) = 
        userPrefs.setPreference(UserPreferencesManager.Keys.DARK_MODE, isDark)

    override suspend fun resetDarkModePreference() = 
        userPrefs.removePreference(UserPreferencesManager.Keys.DARK_MODE)

    override fun getSystemThemePreference(): Flow<Boolean> = 
        userPrefs.getPreference(UserPreferencesManager.Keys.SYSTEM_THEME, true)

    override suspend fun setSystemThemePreference(isSystem: Boolean) = 
        userPrefs.setPreference(UserPreferencesManager.Keys.SYSTEM_THEME, isSystem)

    override suspend fun resetSystemThemePreference() = 
        userPrefs.removePreference(UserPreferencesManager.Keys.SYSTEM_THEME)

    override suspend fun wasNotificationPermissionRequested(): Boolean = 
        userPrefs.getPreference(UserPreferencesManager.Keys.NOTIFICATION_REQUESTED, false).first()

    override suspend fun setNotificationPermissionRequested(requested: Boolean) = 
        userPrefs.setPreference(UserPreferencesManager.Keys.NOTIFICATION_REQUESTED, requested)

    override fun isGlobalTutorialEnabled(): Flow<Boolean> = 
        userPrefs.getPreference(UserPreferencesManager.Keys.GLOBAL_TUTORIAL, true)

    override suspend fun setGlobalTutorialEnabled(enabled: Boolean) = 
        userPrefs.setPreference(UserPreferencesManager.Keys.GLOBAL_TUTORIAL, enabled)

    override suspend fun isTagTutorialCompleted(): Boolean = 
        userPrefs.getPreference(UserPreferencesManager.Keys.TAG_TUTORIAL_COMPLETED, false).first()

    override suspend fun setTagTutorialCompleted(completed: Boolean) = 
        userPrefs.setPreference(UserPreferencesManager.Keys.TAG_TUTORIAL_COMPLETED, completed)
}
