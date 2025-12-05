package com.soujunior.data.mock

import android.content.ContentValues.TAG
import android.util.Log
import com.soujunior.domain.model.request.AwaitingCodeModel
import com.soujunior.domain.model.request.ChangePasswordModel
import com.soujunior.domain.model.request.ForgotPasswordModel
import com.soujunior.domain.model.request.LoginModel
import com.soujunior.domain.model.request.SignUpModel
import com.soujunior.domain.model.response.AccessTokenResponse
import com.soujunior.domain.model.response.MessageResponse
import com.soujunior.domain.model.response.UserInfoResponse
import com.soujunior.domain.network.NetworkResult
import com.soujunior.domain.repository.AuthRepository

class MockAuthRepositoryImpl : AuthRepository  {
    override suspend fun signUp(signUpModel: SignUpModel): NetworkResult<UserInfoResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun login(loginModel: LoginModel): NetworkResult<AccessTokenResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun changePassword(changePasswordModel: ChangePasswordModel): NetworkResult<MessageResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun forgotPassword(forgotPasswordModel: ForgotPasswordModel): NetworkResult<MessageResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun awaitingCode(awaitingCodeModel: AwaitingCodeModel): NetworkResult<AccessTokenResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun saveToken(token: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun deleteToken(): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getToken() = "tokenfake"

    override suspend fun savePassword(password: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getSavedPassword(): String? {
        TODO("Not yet implemented")
    }

    override suspend fun logout() {
        Log.d(TAG, "Logout")

    }
}