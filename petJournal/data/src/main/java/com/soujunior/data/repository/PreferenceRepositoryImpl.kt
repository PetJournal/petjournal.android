package com.soujunior.data.repository

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.soujunior.domain.repository.PreferenceRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class PreferenceRepositoryImpl(
    context: Context
) : PreferenceRepository {

    private val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    companion object {
        private const val KEY_DARK_MODE = "dark_mode_pref_key"
        private const val KEY_SYSTEM_THEME = "system_theme_pref_key"
        private const val KEY_NOTIFICATION_PERMISSION_REQUESTED = "notification_permission_requested"
        private const val KEY_GLOBAL_TUTORIAL_ENABLED = "global_tutorial_enabled"
        private const val KEY_TAG_TUTORIAL_COMPLETED = "tag_tutorial_completed"
    }

    override fun getDarkModePreference(): Flow<Boolean> = callbackFlow {
        // Emit initial value
        trySend(prefs.getBoolean(KEY_DARK_MODE, false))

        val listener = SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
            if (key == KEY_DARK_MODE) {
                trySend(sharedPreferences.getBoolean(KEY_DARK_MODE, false))
            }
        }

        prefs.registerOnSharedPreferenceChangeListener(listener)

        awaitClose {
            prefs.unregisterOnSharedPreferenceChangeListener(listener)
        }
    }

    override suspend fun setDarkModePreference(isDark: Boolean) {
        prefs.edit().putBoolean(KEY_DARK_MODE, isDark).apply()
    }

    override suspend fun resetDarkModePreference() {
        prefs.edit().remove(KEY_DARK_MODE).apply()
    }

    override fun getSystemThemePreference(): Flow<Boolean> = callbackFlow {
        trySend(prefs.getBoolean(KEY_SYSTEM_THEME, true))

        val listener = SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
            if (key == KEY_SYSTEM_THEME) {
                trySend(sharedPreferences.getBoolean(KEY_SYSTEM_THEME, true))
            }
        }

        prefs.registerOnSharedPreferenceChangeListener(listener)

        awaitClose {
            prefs.unregisterOnSharedPreferenceChangeListener(listener)
        }
    }

    override suspend fun setSystemThemePreference(isSystem: Boolean) {
        prefs.edit().putBoolean(KEY_SYSTEM_THEME, isSystem).apply()
    }

    override suspend fun resetSystemThemePreference() {
        prefs.edit().remove(KEY_SYSTEM_THEME).apply()
    }

    override suspend fun wasNotificationPermissionRequested(): Boolean {
        return prefs.getBoolean(KEY_NOTIFICATION_PERMISSION_REQUESTED, false)
    }

    override suspend fun setNotificationPermissionRequested(requested: Boolean) {
        prefs.edit().putBoolean(KEY_NOTIFICATION_PERMISSION_REQUESTED, requested).apply()
    }

    override fun isGlobalTutorialEnabled(): Flow<Boolean> = callbackFlow {
        trySend(prefs.getBoolean(KEY_GLOBAL_TUTORIAL_ENABLED, true))

        val listener = SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
            if (key == KEY_GLOBAL_TUTORIAL_ENABLED) {
                trySend(sharedPreferences.getBoolean(KEY_GLOBAL_TUTORIAL_ENABLED, true))
            }
        }

        prefs.registerOnSharedPreferenceChangeListener(listener)

        awaitClose {
            prefs.unregisterOnSharedPreferenceChangeListener(listener)
        }
    }

    override suspend fun setGlobalTutorialEnabled(enabled: Boolean) {
        prefs.edit().putBoolean(KEY_GLOBAL_TUTORIAL_ENABLED, enabled).apply()
    }

    override suspend fun isTagTutorialCompleted(): Boolean {
        return prefs.getBoolean(KEY_TAG_TUTORIAL_COMPLETED, false)
    }

    override suspend fun setTagTutorialCompleted(completed: Boolean) {
        prefs.edit().putBoolean(KEY_TAG_TUTORIAL_COMPLETED, completed).apply()
    }
}
