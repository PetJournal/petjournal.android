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
        val TASKS_PERIOD_DAILY = longPreferencesKey("listTasksByPeriodDaily")
        val TASKS_PERIOD_WEEKLY = longPreferencesKey("listTasksByPeriodWeekly")
        val TASKS_PERIOD_MONTHLY = longPreferencesKey("listTasksByPeriodMonthly")
        val TASKS_PERIOD_CUSTOM = longPreferencesKey("listTasksByPeriodCustom")
        val TASKS_NEXT_PET = longPreferencesKey("getNextEventsForPet")
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
        invalidateCache(SyncKeys.TASKS_PERIOD_DAILY)
        invalidateCache(SyncKeys.TASKS_PERIOD_WEEKLY)
        invalidateCache(SyncKeys.TASKS_PERIOD_MONTHLY)
        invalidateCache(SyncKeys.TASKS_PERIOD_CUSTOM)
    }

    suspend fun invalidateOtherTaskCaches(currentKey: Preferences.Key<Long>) {
        if (currentKey != SyncKeys.TASKS_PERIOD_DAILY) invalidateCache(SyncKeys.TASKS_PERIOD_DAILY)
        if (currentKey != SyncKeys.TASKS_PERIOD_WEEKLY) invalidateCache(SyncKeys.TASKS_PERIOD_WEEKLY)
        if (currentKey != SyncKeys.TASKS_PERIOD_MONTHLY) invalidateCache(SyncKeys.TASKS_PERIOD_MONTHLY)
        if (currentKey != SyncKeys.TASKS_PERIOD_CUSTOM) invalidateCache(SyncKeys.TASKS_PERIOD_CUSTOM)
        if (currentKey != SyncKeys.TASKS_NEXT_PET) invalidateCache(SyncKeys.TASKS_NEXT_PET)
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