package com.soujunior.domain.use_case.auth

import android.content.ContentValues.TAG
import android.util.Log
import com.soujunior.domain.model.request.LoginModel
import com.soujunior.domain.network.NetworkResult
import com.soujunior.domain.repository.AuthRepository
import com.soujunior.domain.use_case.base.BaseUseCase
import com.soujunior.domain.use_case.base.DataResult

class LoginUseCase (private val repository : AuthRepository): BaseUseCase<LoginModel, String>() {
    override suspend fun doWork(value: LoginModel): DataResult<String> {
        return when(val response = repository.login(value)) {
            is NetworkResult.Success -> {
                val success = repository.saveToken(response.data.accessToken)
                if (success) {
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