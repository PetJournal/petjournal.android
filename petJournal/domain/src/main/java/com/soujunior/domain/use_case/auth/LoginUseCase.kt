package com.soujunior.domain.use_case.auth

import com.soujunior.domain.model.request.LoginModel
import com.soujunior.domain.network.NetworkResult
import com.soujunior.domain.repository.api.AuthRepository
import com.soujunior.domain.repository.api.Repository
import com.soujunior.domain.use_case.base.BaseUseCase
import com.soujunior.domain.use_case.base.DataResult

class LoginUseCase(
    private val authRepository: AuthRepository,
    private val repository: Repository
) : BaseUseCase<LoginModel, String>() {
    override suspend fun doWork(value: LoginModel): DataResult<String> {
        return when (val response = authRepository.login(value)) {
            is NetworkResult.Success -> {
                val success = authRepository.saveToken(response.data.accessToken)
                if (success) {
                    repository.getGuardianName(true)
                    DataResult.Success("Token Saved")
                } else {
                    DataResult.Failure(Throwable("Error in Save Token!"))
                }
            }
            is NetworkResult.Error -> DataResult.Failure(Throwable(message = "${response.code} -> ${response.body?.error}"))
            is NetworkResult.Exception -> DataResult.Failure(response.e)
        }
    }
}
