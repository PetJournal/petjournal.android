package com.soujunior.data.mock

import com.soujunior.data.remote.AuthDataSource
import com.soujunior.domain.model.request.AwaitingCodeModel
import com.soujunior.domain.model.request.ChangePasswordModel
import com.soujunior.domain.model.request.ForgotPasswordModel
import com.soujunior.domain.model.request.LoginModel
import com.soujunior.domain.model.request.SignUpModel
import com.soujunior.domain.model.response.AccessTokenResponse
import com.soujunior.domain.model.response.MessageResponse
import com.soujunior.domain.model.response.UserInfoResponse
import com.soujunior.domain.network.NetworkResult
import kotlinx.coroutines.delay

class MockAuthDataSource : AuthDataSource {
    
    override suspend fun signUp(signUpBody: SignUpModel): NetworkResult<UserInfoResponse> {
        delay(1000) // Simula delay da rede
        return NetworkResult.Success(MockDataProvider.getMockUserInfoResponse())
    }

    override suspend fun login(loginBody: LoginModel): NetworkResult<AccessTokenResponse> {
        delay(1000) // Simula delay da rede
        
        // Validação fake - só aceita o email e senha específicos
        return if (loginBody.email == MockDataProvider.MOCK_EMAIL && 
                   loginBody.password == MockDataProvider.MOCK_PASSWORD) {
            NetworkResult.Success(MockDataProvider.getMockAccessTokenResponse())
        } else {
            NetworkResult.Error(
                code = 401,
                body = com.soujunior.domain.network.ErrorBody("Unauthorized")
            )
        }
    }

    override suspend fun changePassword(
        token: String,
        changePasswordBody: ChangePasswordModel
    ): NetworkResult<MessageResponse> {
        delay(1000)
        return NetworkResult.Success(MockDataProvider.getMockMessageResponse("Senha alterada com sucesso"))
    }

    override suspend fun forgotPassword(forgotPasswordBody: ForgotPasswordModel): NetworkResult<MessageResponse> {
        delay(1000)
        return NetworkResult.Success(MockDataProvider.getMockMessageResponse("Email de recuperação enviado"))
    }

    override suspend fun waitingCode(waitingCodeBody: AwaitingCodeModel): NetworkResult<AccessTokenResponse> {
        delay(1000)
        return NetworkResult.Success(MockDataProvider.getMockAccessTokenResponse())
    }
}

