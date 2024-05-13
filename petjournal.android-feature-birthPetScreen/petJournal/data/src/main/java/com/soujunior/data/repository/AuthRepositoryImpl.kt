package com.soujunior.data.repository

import android.content.ContentValues.TAG
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.preference.PreferenceManager
import com.soujunior.data.remote.AuthService
import com.soujunior.data.util.manager.JwtManager
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
import com.soujunior.domain.repository.GuardianLocalDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepositoryImpl(
    private val authApi: AuthService,
    private val guardianLocalDataSourceImpl: GuardianLocalDataSource,
    context: Context
) : AuthRepository {

    private val jwtManager: JwtManager = JwtManager.getInstance(context)

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

        return authApi.changePassword("Bearer " + getToken(), changePasswordModel)
    }

    override suspend fun forgotPassword(forgotPasswordModel: ForgotPasswordModel): NetworkResult<MessageResponse> {
        return authApi.forgotPassword(forgotPasswordModel)
    }

    override suspend fun awaitingCode(awaitingCodeModel: AwaitingCodeModel): NetworkResult<AccessTokenResponse> {
        return authApi.waitingCode(awaitingCodeModel)
    }

    override suspend fun savePassword(password: String) {

        prefs.edit().putString("password", password).apply()
    }

    override suspend fun getSavedPassword(): String? {
        return prefs.getString("password", null)
    }

    override suspend fun logout() {
        jwtManager.deleteToken()
        guardianLocalDataSourceImpl.deleteDatabase()
    }

    override suspend fun saveToken(token: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                jwtManager.setToken(token)
                true
            } catch (e: Exception) {
                Log.e(TAG, "AuthRepositoryImpl-saveToken: ${e.message}", e)
                false
            }
        }
    }

    override suspend fun getToken(): String? {
        return try {
            jwtManager.getToken()
        } catch (e: Exception) {
            Log.e("AuthRepositoryImpl-getToken", e.message.toString())
            null
        }
    }

    override suspend fun deleteToken(): Boolean {
        return try {
            jwtManager.deleteToken()
            true
        } catch (e: Exception) {
            Log.e("AuthRepositoryImpl-deleteToken", e.message.toString())
            false
        }
    }
}