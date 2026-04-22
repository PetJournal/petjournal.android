package com.soujunior.domain.use_case.auth

import com.soujunior.domain.repository.api.AuthRepository
import com.soujunior.domain.repository.PreferenceRepository

class LogoutUseCase(
    private val authRepository: AuthRepository,
    private val preferenceRepository: PreferenceRepository
) {
    suspend fun doWork() {
        authRepository.logout()
        preferenceRepository.resetDarkModePreference()
    }
}
