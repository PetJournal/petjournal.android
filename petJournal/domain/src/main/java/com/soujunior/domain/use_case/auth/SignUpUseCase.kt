package com.soujunior.domain.use_case.auth

import com.soujunior.data.model.request.SignUpModel
import com.soujunior.data.model.response.UserInfoResponse
import com.soujunior.domain.model.User
import com.soujunior.data.repository.AuthRepository
import com.soujunior.data.util.network.NetworkResult
import com.soujunior.domain.use_case.base.BaseUseCase
import com.soujunior.domain.use_case.base.DataResult

class SignUpUseCase(
    private val repository: AuthRepository
): BaseUseCase<SignUpModel, User>() {

    override suspend fun doWork(value: SignUpModel): DataResult<User> {
        return repository.signUp(value).toData()
    }

    fun NetworkResult<UserInfoResponse>.toData(): DataResult<User> {
        return when(this) {
            is NetworkResult.Success -> DataResult.Success(this.data.toUI())
            is NetworkResult.Error -> DataResult.Failure(Throwable(message = "${this.code} -> ${this.body?.error}"))
            is NetworkResult.Exception -> DataResult.Failure(this.e)
        }
    }

    fun UserInfoResponse.toUI() = User(
        id = this.id,
        firstName = this.firstName,
        lastName = this.lastName,
        email = this.email,
        phone = this.phone
    )
}