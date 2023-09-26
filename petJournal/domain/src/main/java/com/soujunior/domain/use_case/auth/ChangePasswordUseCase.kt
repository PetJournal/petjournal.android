package com.soujunior.domain.use_case.auth

import com.soujunior.data.model.response.MessageResponse
import com.soujunior.domain.model.request.ChangePasswordModel
import com.soujunior.domain.network.NetworkResult
import com.soujunior.domain.repository.AuthRepository
import com.soujunior.domain.use_case.base.BaseUseCase
import com.soujunior.domain.use_case.base.DataResult


class ChangePasswordUseCase(
    private val repository: AuthRepository
) : BaseUseCase<ChangePasswordModel, String>() {

    override suspend fun doWork(value: ChangePasswordModel): DataResult<String> {
        return repository.changePassword(value).toData()
    }

    private fun NetworkResult<MessageResponse>.toData(): DataResult<String> {
        return when (this) {
            is NetworkResult.Success -> DataResult.Success(this.data.message)
            is NetworkResult.Error -> DataResult.Failure(Throwable(message = "${this.code} -> ${this.body?.error}"))
            is NetworkResult.Exception -> DataResult.Failure(this.e)
        }
    }
}