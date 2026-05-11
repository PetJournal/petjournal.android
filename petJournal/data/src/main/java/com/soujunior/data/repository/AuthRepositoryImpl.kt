package com.soujunior.data.repository

import android.content.Context
import com.soujunior.data.util.manager.UserPreferencesManager
import kotlinx.coroutines.flow.first
import com.soujunior.data.remote.AuthDataSource
import com.soujunior.data.util.manager.JwtManager
import com.soujunior.domain.model.request.AwaitingCodeModel
import com.soujunior.domain.model.request.ChangePasswordModel
import com.soujunior.domain.model.request.ForgotPasswordModel
import com.soujunior.domain.model.request.LoginModel
import com.soujunior.domain.model.request.LoginPreferenceModel
import com.soujunior.domain.model.request.SignUpModel
import com.soujunior.domain.model.response.AccessTokenResponse
import com.soujunior.domain.model.response.MessageResponse
import com.soujunior.domain.model.response.UserInfoResponse
import com.soujunior.domain.network.NetworkResult
import com.soujunior.domain.repository.api.AuthRepository
import com.soujunior.domain.repository.database.LocalDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepositoryImpl(
    private val authApi: AuthDataSource,
    private val guardianLocalDataSourceImpl: LocalDataSource,
    context: Context
) : AuthRepository {

    private val jwtManager: JwtManager = JwtManager.getInstance(context)
    private val userPrefs = UserPreferencesManager.getInstance(context)

    override suspend fun signUp(signUpModel: SignUpModel): NetworkResult<UserInfoResponse> {
        return authApi.signUp(signUpModel)
    }

    override suspend fun login(loginModel: LoginModel): NetworkResult<AccessTokenResponse> {
        val result = authApi.login(loginModel)
        if (result is NetworkResult.Success) {
            userPrefs.setPreference(UserPreferencesManager.Keys.IS_LOGIN, true)
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

    override suspend fun saveLoginPreference(model: LoginPreferenceModel) {
        userPrefs.setPreference(UserPreferencesManager.Keys.LOGIN_EMAIL, model.email)
        userPrefs.setPreference(UserPreferencesManager.Keys.LOGIN_PASSWORD, model.password)
        userPrefs.setPreference(UserPreferencesManager.Keys.LOGIN_IS_REMEMBER, model.isRemember)
    }

    override suspend fun getLoginPreference(): LoginPreferenceModel? {
        val email = userPrefs.getPreference(UserPreferencesManager.Keys.LOGIN_EMAIL, "").first()
        val password = userPrefs.getPreference(UserPreferencesManager.Keys.LOGIN_PASSWORD, "").first()
        val isRemember = userPrefs.getPreference(UserPreferencesManager.Keys.LOGIN_IS_REMEMBER, false).first()

        return if (email.isNotEmpty() && password.isNotEmpty()) {
            LoginPreferenceModel(email, password, isRemember)
        } else null
    }

    override suspend fun logout() {
        jwtManager.deleteToken()
        guardianLocalDataSourceImpl.deleteDatabase()
        deleteToken()
        userPrefs.removePreference(UserPreferencesManager.Keys.IS_LOGIN)
    }

    override suspend fun saveToken(token: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                jwtManager.setToken(token)
                true
            } catch (e: Exception) {
                false
            }
        }
    }

    override suspend fun getToken(): String? {
        return try {
            jwtManager.getToken()
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun deleteToken(): Boolean {
        return try {
            jwtManager.deleteToken()
            true
        } catch (e: Exception) {
            false
        }
    }
}