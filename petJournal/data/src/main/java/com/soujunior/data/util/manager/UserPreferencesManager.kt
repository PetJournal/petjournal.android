package com.soujunior.data.util.manager

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.userPrefsDataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

class UserPreferencesManager private constructor(private val context: Context) {

    object Keys {
        val DARK_MODE = booleanPreferencesKey("dark_mode_pref_key")
        val SYSTEM_THEME = booleanPreferencesKey("system_theme_pref_key")
        val NOTIFICATION_REQUESTED = booleanPreferencesKey("notification_permission_requested")
        val GLOBAL_TUTORIAL = booleanPreferencesKey("global_tutorial_enabled")
        val TAG_TUTORIAL_COMPLETED = booleanPreferencesKey("tag_tutorial_completed")

        val IS_LOGIN = booleanPreferencesKey("Islogin")
        val LOGIN_EMAIL = stringPreferencesKey("email")
        val LOGIN_PASSWORD = stringPreferencesKey("password")
        val LOGIN_IS_REMEMBER = booleanPreferencesKey("isRemember")
    }

    fun <T> getPreference(key: Preferences.Key<T>, defaultValue: T): Flow<T> {
        return context.userPrefsDataStore.data.map { preferences ->
            preferences[key] ?: defaultValue
        }
    }

    suspend fun <T> setPreference(key: Preferences.Key<T>, value: T) {
        context.userPrefsDataStore.edit { preferences ->
            preferences[key] = value
        }
    }

    suspend fun <T> removePreference(key: Preferences.Key<T>) {
        context.userPrefsDataStore.edit { preferences ->
            preferences.remove(key)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreferencesManager? = null
        fun getInstance(context: Context): UserPreferencesManager =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: UserPreferencesManager(context.applicationContext).also { INSTANCE = it }
            }
    }
}