package com.soujunior.data.util.manager

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "sync_prefs")

class SyncDataManager private constructor(private val context: Context) {

    object SyncKeys {
        val GUARDIAN_NAME = longPreferencesKey("getGuardianName")
        val LIST_TAG = longPreferencesKey("getListTag")
        val LIST_PET = longPreferencesKey("getListPet")
        val LIST_PET_SIZES = longPreferencesKey("getListPetSizes")
        val LIST_PET_RACES = longPreferencesKey("getListPetRaces")
        val TASKS_PERIOD = longPreferencesKey("listTasksByPeriod")
    }

    suspend fun saveSyncTime(key: Preferences.Key<Long>, timestamp: Long = System.currentTimeMillis()) {
        context.dataStore.edit { preferences ->
            preferences[key] = timestamp
        }
    }

    fun getLastSyncTime(key: Preferences.Key<Long>): Flow<Long?> {
        return context.dataStore.data.map { preferences ->
            preferences[key]
        }
    }

    suspend fun invalidateCache(key: Preferences.Key<Long>) {
        context.dataStore.edit { preferences ->
            preferences[key] = 0L
        }
    }

    suspend fun invalidateTasksPeriodCache() {
        invalidateCache(SyncKeys.TASKS_PERIOD)
    }

    suspend fun invalidateListPetCache() {
        invalidateCache(SyncKeys.LIST_PET)
    }

    companion object {
        @Volatile
        private var INSTANCE: SyncDataManager? = null

        fun getInstance(context: Context): SyncDataManager =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: SyncDataManager(context.applicationContext).also { INSTANCE = it }
            }
    }
}