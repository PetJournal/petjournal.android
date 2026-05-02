package com.soujunior.domain.use_case.preference

import com.soujunior.domain.repository.PreferenceRepository

class CheckNotificationPermissionRequestedUseCase(
    private val preferenceRepository: PreferenceRepository
) {
    suspend operator fun invoke(): Boolean {
        return preferenceRepository.wasNotificationPermissionRequested()
    }
}
