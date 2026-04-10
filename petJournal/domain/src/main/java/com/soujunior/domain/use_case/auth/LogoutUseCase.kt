package com.soujunior.domain.use_case.auth

import com.soujunior.domain.repository.api.AuthRepository

class LogoutUseCase( private val authRepository: AuthRepository ) {
    suspend fun doWork() { authRepository.logout() }
}
