package com.soujunior.domain.use_case.preference

import com.soujunior.domain.repository.PreferenceRepository

class SaveDarkModePreferenceUseCase(
    private val preferenceRepository: PreferenceRepository
) {
    suspend operator fun invoke(isDark: Boolean) {
        preferenceRepository.setDarkModePreference(isDark)
    }
}
