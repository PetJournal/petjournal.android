package com.soujunior.domain.repository.api

interface FeatureFlagRepository {
    suspend fun isFeedbackEnabled(): Boolean
}
