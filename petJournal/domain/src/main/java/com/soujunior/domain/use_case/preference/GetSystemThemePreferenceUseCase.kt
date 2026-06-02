package com.soujunior.domain.use_case.preference

import com.soujunior.domain.repository.PreferenceRepository
import kotlinx.coroutines.flow.Flow

class GetSystemThemePreferenceUseCase(
    private val preferenceRepository: PreferenceRepository
) {
    operator fun invoke(): Flow<Boolean> {
        return preferenceRepository.getSystemThemePreference()
    }
}
