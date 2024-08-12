package com.soujunior.domain.use_case.auth

import android.content.ContentValues.TAG
import android.util.Log
import com.soujunior.domain.model.mapper.User
import com.soujunior.domain.model.request.SignUpModel
import com.soujunior.domain.model.response.UserInfoResponse
import com.soujunior.domain.network.NetworkResult
import com.soujunior.domain.repository.AuthRepository
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
            is NetworkResult.Success -> {
                Log.e(TAG, data.toString())
                DataResult.Success(this.data.toUI())
            }
            is NetworkResult.Error -> {
                DataResult.Failure(Throwable(message = "${this.code} -> ${this.body?.error}"))
            }
            is NetworkResult.Exception -> {
                //Log.e(TAG, "Exception: "+this.toString())
                DataResult.Failure(this.e)
            }
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