package com.soujunior.domain.use_case.auth

import com.soujunior.domain.repository.api.AuthRepository

class GetSavedPasswordUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): String? {
        return authRepository.getSavedPassword()
    }
}
