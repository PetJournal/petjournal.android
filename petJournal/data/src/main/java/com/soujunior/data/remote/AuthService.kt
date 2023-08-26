package com.soujunior.data.remote

import com.soujunior.domain.network.NetworkResult
import com.soujunior.data.model.request.ChangePasswordModel
import com.soujunior.data.model.request.ForgotPasswordModel
import com.soujunior.data.model.request.LoginModel
import com.soujunior.data.model.request.SignUpModel
import com.soujunior.data.model.request.AwaitingCodeModel
import com.soujunior.data.model.response.AccessTokenResponse
import com.soujunior.data.model.response.MessageResponse
import com.soujunior.data.model.response.UserInfoResponse
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.POST

interface AuthService {

    @POST("api/signup")
    suspend fun signUp(@Body signUpBody: SignUpModel): NetworkResult<UserInfoResponse>

    @POST("api/login")
    suspend fun login(@Body loginBody: LoginModel): NetworkResult<AccessTokenResponse>

    @PATCH("api/guardian/change-password")
    suspend fun changePassword(@Body changePasswordBody: ChangePasswordModel): NetworkResult<MessageResponse>

    @POST("api/forget-password")
    suspend fun forgotPassword(@Body forgotPasswordBody: ForgotPasswordModel): NetworkResult<MessageResponse>

    @POST("api/waiting-code")
    suspend fun waitingCode(@Body waitingCodeBody: AwaitingCodeModel): NetworkResult<AccessTokenResponse>
}