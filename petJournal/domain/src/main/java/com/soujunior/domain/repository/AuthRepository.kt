package com.soujunior.domain.repository

import com.soujunior.domain.model.request.AwaitingCodeModel
import com.soujunior.domain.model.request.ChangePasswordModel
import com.soujunior.domain.model.request.ForgotPasswordModel
import com.soujunior.domain.model.request.LoginModel
import com.soujunior.domain.model.request.SignUpModel
import com.soujunior.domain.model.response.AccessTokenResponse
import com.soujunior.data.model.response.MessageResponse
import com.soujunior.data.model.response.UserInfoResponse
import com.soujunior.domain.network.NetworkResult

interface AuthRepository {
    suspend fun signUp(signUpModel: SignUpModel): NetworkResult<UserInfoResponse>

    suspend fun login(loginModel: LoginModel): NetworkResult<AccessTokenResponse>

    suspend fun changePassword(changePasswordModel: ChangePasswordModel): NetworkResult<MessageResponse>

    suspend fun forgotPassword(forgotPasswordModel: ForgotPasswordModel): NetworkResult<MessageResponse>

    suspend fun awaitingCode(awaitingCodeModel: AwaitingCodeModel): NetworkResult<AccessTokenResponse>

    suspend fun saveToken(token: String): Boolean

    suspend fun getToken(): String?
    suspend fun savePassword(password: String)
    suspend fun getSavedPassword(): String?
}