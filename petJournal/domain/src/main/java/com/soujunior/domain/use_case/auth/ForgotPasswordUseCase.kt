package com.soujunior.domain.use_case.auth

import com.soujunior.domain.model.request.ForgotPasswordModel
import com.soujunior.data.model.response.MessageResponse
import com.soujunior.domain.network.NetworkResult
import com.soujunior.domain.repository.AuthRepository
import com.soujunior.domain.use_case.base.BaseUseCase
import com.soujunior.domain.use_case.base.DataResult

class ForgotPasswordUseCase(
    private val repository: AuthRepository
): BaseUseCase<ForgotPasswordModel, String>() {

    override suspend fun doWork(value: ForgotPasswordModel): DataResult<String> {
        return repository.forgotPassword(value).toData()
    }

    fun NetworkResult<MessageResponse>.toData(): DataResult<String> {
        return when(this) {
            is NetworkResult.Success -> DataResult.Success(this.data.message)
            is NetworkResult.Error -> DataResult.Failure(Throwable(message = "${this.code} -> ${this.body?.error}"))
            is NetworkResult.Exception -> DataResult.Failure(this.e)
        }
    }
}