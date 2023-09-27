package com.soujunior.data.remote


import com.soujunior.domain.model.request.AwaitingCodeModel
import com.soujunior.domain.model.request.ChangePasswordModel
import com.soujunior.domain.model.request.ForgotPasswordModel
import com.soujunior.domain.model.request.LoginModel
import com.soujunior.domain.model.request.SignUpModel
import com.soujunior.domain.model.response.AccessTokenResponse
import com.soujunior.domain.model.response.GuardianNameResponse
import com.soujunior.domain.model.response.MessageResponse
import com.soujunior.domain.model.response.UserInfoResponse
import com.soujunior.domain.network.NetworkResult
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST

interface AuthService {

    @POST("api/signup")
    suspend fun signUp(@Body signUpBody: SignUpModel): NetworkResult<UserInfoResponse>

    @POST("api/login")
    suspend fun login(@Body loginBody: LoginModel): NetworkResult<AccessTokenResponse>

    @PATCH("api/guardian/change-password")
    suspend fun changePassword(
        @Header("Authorization") token: String,
        @Body changePasswordBody: ChangePasswordModel
    ): NetworkResult<MessageResponse>

    @POST("api/forget-password")
    suspend fun forgotPassword(@Body forgotPasswordBody: ForgotPasswordModel): NetworkResult<MessageResponse>

    @POST("api/waiting-code")
    suspend fun waitingCode(@Body waitingCodeBody: AwaitingCodeModel): NetworkResult<AccessTokenResponse>
}