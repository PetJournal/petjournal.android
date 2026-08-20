package com.soujunior.domain.use_case.feedback

import com.soujunior.domain.repository.api.FeatureFlagRepository

class GetFeedbackFeatureFlagUseCase(
    private val repository: FeatureFlagRepository
) {
    suspend operator fun invoke(): Boolean {
        return repository.isFeedbackEnabled()
    }
}
