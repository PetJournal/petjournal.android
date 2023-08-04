package com.soujunior.data.api

import com.soujunior.data.model.NetworkResult
import com.soujunior.data.model.response.LoginResponse
import com.soujunior.data.model.response.SignUpResponse
import com.soujunior.domain.entities.auth.LoginModel
import com.soujunior.domain.entities.auth.RegisterModel
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("login")
    suspend fun login(@Body loginBody: LoginModel): NetworkResult<LoginResponse>

    @POST("signup")
    suspend fun signUp(@Body registerBody: RegisterModel): NetworkResult<SignUpResponse>
}