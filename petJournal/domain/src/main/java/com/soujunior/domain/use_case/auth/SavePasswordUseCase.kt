package com.soujunior.domain.use_case.auth

import com.soujunior.domain.repository.AuthRepository
import com.soujunior.domain.use_case.base.BaseUseCase
import com.soujunior.domain.use_case.base.DataResult

class SavePasswordUseCase(
    private val authRepository: AuthRepository
) : BaseUseCase<String, Unit>() {

    override suspend fun doWork(password: String): DataResult<Unit> {
        return try {
            authRepository.savePassword(password)
            DataResult.Success(Unit)
        } catch (e: Exception) {
            DataResult.Failure(e)
        }
    }
}
