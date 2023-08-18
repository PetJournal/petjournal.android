package com.soujunior.data.api

import retrofit2.Call
import com.soujunior.domain.entities.auth.ApiResponseCode
import com.soujunior.domain.entities.auth.AwaitingCodeModel
import com.soujunior.domain.entities.auth.ForgotPasswordModel
import com.soujunior.domain.entities.auth.LoginModel
import com.soujunior.domain.entities.auth.PasswordModel
import com.soujunior.domain.entities.auth.RegisterModel
import com.soujunior.domain.entities.auth.UserModel
import retrofit2.http.*

interface Service {
    @POST("api/categories")
    fun register(@Body registerData: RegisterModel): Call<ApiResponseCode>

    @POST("api/login")
    fun login(@Body loginData: LoginModel) : Call<ApiResponseCode>

    @POST("api/awaitingCode")
    fun awaitingCode(@Body awaitingCode: AwaitingCodeModel) : Call<ApiResponseCode>

    @POST("api/forgotPassword")
    fun forgotPassword(@Body forgotPassword : ForgotPasswordModel) : Call<ApiResponseCode>

    @POST("api/changePassword")
    fun changePassword(@Body password : PasswordModel) : Call<ApiResponseCode>

    @GET ("api/user")
    fun getUserName(@Body name : UserModel) : Call<ApiResponseCode>
}