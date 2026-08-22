package com.soujunior.data.repository

import android.util.Log
import com.soujunior.data.remote.FeatureFlagService
import com.soujunior.data.BuildConfig
import com.soujunior.domain.repository.api.FeatureFlagRepository

class FeatureFlagRepositoryImpl(
    private val service: FeatureFlagService
) : FeatureFlagRepository {

    override suspend fun isFeedbackEnabled(): Boolean {
        return try {
            if (BuildConfig.GIST_RAW_URL.isBlank()) {
                Log.e("FeatureFlagRepo", "GIST_RAW_URL is empty")
                return false
            }
            val flags = service.getFeatureFlags(BuildConfig.GIST_RAW_URL)
            flags["com.soujunior.petjournal"] ?: false
        } catch (e: Exception) {
            Log.e("FeatureFlagRepo", "Error fetching feature flags: ${e.message}", e)
            false
        }
    }
}
