package com.soujunior.domain.use_case.preference

import com.soujunior.domain.repository.PreferenceRepository

class SaveSystemThemePreferenceUseCase(
    private val preferenceRepository: PreferenceRepository
) {
    suspend operator fun invoke(isSystem: Boolean) {
        preferenceRepository.setSystemThemePreference(isSystem)
    }
}
