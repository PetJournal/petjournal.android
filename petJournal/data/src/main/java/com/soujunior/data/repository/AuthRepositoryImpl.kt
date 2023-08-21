package com.soujunior.data.repository

import android.content.Context
import android.util.Log
import com.soujunior.data.remote.AuthService
import com.soujunior.data.util.network.NetworkResult
import com.soujunior.data.model.request.ChangePasswordModel
import com.soujunior.data.model.request.ForgotPasswordModel
import com.soujunior.data.model.request.LoginModel
import com.soujunior.data.model.request.SignUpModel
import com.soujunior.data.model.request.AwaitingCodeModel
import com.soujunior.data.model.response.AccessTokenResponse
import com.soujunior.data.model.response.MessageResponse
import com.soujunior.data.model.response.UserInfoResponse
import com.soujunior.data.util.manager.JwtManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AuthRepositoryImpl(
    private val authApi: AuthService,
    private val context: Context
) : AuthRepository {

    private val jwtManager: JwtManager = JwtManager(context)

    override suspend fun signUp(signUpModel: SignUpModel): NetworkResult<UserInfoResponse> {
        return authApi.signUp(signUpModel)
    }

    override suspend fun login(loginModel: LoginModel): NetworkResult<AccessTokenResponse> {
        return authApi.login(loginModel)
    }

    override suspend fun changePassword(changePasswordModel: ChangePasswordModel): NetworkResult<MessageResponse> {
        return authApi.changePassword(changePasswordModel)
    }

    override suspend fun forgotPassword(forgotPasswordModel: ForgotPasswordModel): NetworkResult<MessageResponse> {
        return authApi.forgotPassword(forgotPasswordModel)
    }

    override suspend fun awaitingCode(awaitingCodeModel: AwaitingCodeModel): NetworkResult<AccessTokenResponse> {
        return authApi.waitingCode(awaitingCodeModel)
    }

    /* =--= Handle Access Token =--= */

    override suspend fun saveToken(token: String): Boolean {
        var status: Boolean = false

        val saveTokenJob = GlobalScope.launch(Dispatchers.IO) {
            try {
                jwtManager.setToken(token)
                status = true
            } catch (e: Exception) {
                Log.e("AuthRepository", e.message.toString())
                status = false
            }
        }

        saveTokenJob.join() // wait ...
        return status
    }

    override suspend fun getToken(): String? {
        var token: String? = null

        val getTokenJob = GlobalScope.launch(Dispatchers.IO) {
            try {
                token = jwtManager.getToken()
            } catch (e: Exception) {
                Log.e("AuthRepository", e.message.toString())
            }
        }

        getTokenJob.join() // wait ...
        return token
    }
}