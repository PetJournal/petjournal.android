package com.soujunior.domain.use_case.preference

import com.soujunior.domain.repository.PreferenceRepository

class SetNotificationPermissionRequestedUseCase(
    private val preferenceRepository: PreferenceRepository
) {
    suspend operator fun invoke(requested: Boolean) {
        preferenceRepository.setNotificationPermissionRequested(requested)
    }
}
