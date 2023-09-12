package com.soujunior.data.repository

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import android.util.Log
import com.soujunior.data.remote.AuthService
import com.soujunior.domain.network.NetworkResult
import com.soujunior.data.model.response.MessageResponse
import com.soujunior.data.model.response.UserInfoResponse
import com.soujunior.data.util.manager.JwtManager
import com.soujunior.domain.model.request.AwaitingCodeModel
import com.soujunior.domain.model.request.ChangePasswordModel
import com.soujunior.domain.model.request.ForgotPasswordModel
import com.soujunior.domain.model.request.LoginModel
import com.soujunior.domain.model.request.SignUpModel
import com.soujunior.domain.model.response.AccessTokenResponse
import com.soujunior.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AuthRepositoryImpl(
    private val authApi: AuthService,
    private val context: Context
) : AuthRepository {

    private val jwtManager: JwtManager = JwtManager(context)
    private val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    override suspend fun signUp(signUpModel: SignUpModel): NetworkResult<UserInfoResponse> {
        return authApi.signUp(signUpModel)
    }

    override suspend fun login(loginModel: LoginModel): NetworkResult<AccessTokenResponse> {
        val result = authApi.login(loginModel)
        if (result is NetworkResult.Success) {
            prefs.edit().putBoolean("Islogin", true).apply()
        }
        return result
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

    override suspend fun savePassword(password: String) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        prefs.edit().putString("password", password).apply()
    }

    override suspend fun getSavedPassword(): String? {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        return prefs.getString("password", null)
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

    override suspend fun deleteToken(): Boolean {
        var status: Boolean = false

        val deleteTokenJob = GlobalScope.launch(Dispatchers.IO) {
            try {
                jwtManager.deleteToken()
                status = true
            } catch (e: Exception) {
                Log.e("AuthRepository", e.message.toString())
                status = false
            }
        }
        return status
    }

}