package com.soujunior.domain.use_case.auth

import com.soujunior.domain.model.request.LoginPreferenceModel
import com.soujunior.domain.repository.api.AuthRepository
import com.soujunior.domain.use_case.base.BaseUseCase
import com.soujunior.domain.use_case.base.DataResult

class GetLoginPreferenceUseCase(
    private val authRepository: AuthRepository
) : BaseUseCase<Unit, LoginPreferenceModel?>() {

    override suspend fun doWork(value: Unit): DataResult<LoginPreferenceModel?> {
        return try {
            val result = authRepository.getLoginPreference()
            DataResult.Success(result)
        } catch (e: Exception) {
            DataResult.Failure(e)
        }
    }
}
