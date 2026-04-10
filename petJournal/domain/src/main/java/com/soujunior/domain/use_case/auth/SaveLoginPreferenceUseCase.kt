package com.soujunior.domain.use_case.auth

import com.soujunior.domain.model.request.LoginPreferenceModel
import com.soujunior.domain.repository.api.AuthRepository
import com.soujunior.domain.use_case.base.BaseUseCase
import com.soujunior.domain.use_case.base.DataResult

class SaveLoginPreferenceUseCase(
    private val authRepository: AuthRepository
) : BaseUseCase<LoginPreferenceModel, Unit>() {

    override suspend fun doWork(value: LoginPreferenceModel): DataResult<Unit> {
        return try {
            authRepository.saveLoginPreference(value)
            DataResult.Success(Unit)
        } catch (e: Exception) {
            DataResult.Failure(e)
        }
    }
}
