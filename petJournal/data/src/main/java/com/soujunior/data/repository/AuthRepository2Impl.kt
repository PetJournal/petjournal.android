package com.soujunior.data.repository

import com.soujunior.data.api.AuthService
import com.soujunior.data.model.NetworkResult
import com.soujunior.data.model.response.LoginResponse
import com.soujunior.data.model.response.SignUpResponse
import com.soujunior.domain.entities.auth.LoginModel
import com.soujunior.domain.entities.auth.RegisterModel

class AuthRepository2Impl (
    private val authApi: AuthService
) {

    suspend fun login(body: LoginModel): NetworkResult<LoginResponse> = authApi.login(body)

    suspend fun signUp(body: RegisterModel): NetworkResult<SignUpResponse> = authApi.signUp(body)
}