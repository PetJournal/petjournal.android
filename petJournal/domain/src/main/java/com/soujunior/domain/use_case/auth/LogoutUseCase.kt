package com.soujunior.domain.use_case.auth

import com.soujunior.domain.model.request.LoginModel
import com.soujunior.domain.model.response.GuardianNameResponse
import com.soujunior.domain.repository.AuthRepository
import com.soujunior.domain.use_case.base.BaseUseCase
import com.soujunior.domain.use_case.base.DataResult

class LogoutUseCase( private val authRepository: AuthRepository ) {
    suspend fun doWork() { authRepository.logout() }
}
