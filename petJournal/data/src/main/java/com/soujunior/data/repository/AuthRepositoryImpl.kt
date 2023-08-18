package com.soujunior.data.repository

import android.content.Context
import com.soujunior.data.api.Service
import com.soujunior.data.util.Util
import com.soujunior.domain.entities.auth.ApiResponseCode
import com.soujunior.domain.entities.auth.AwaitingCodeModel
import com.soujunior.domain.entities.auth.ForgotPasswordModel
import com.soujunior.domain.entities.auth.LoginModel
import com.soujunior.domain.entities.auth.PasswordModel
import com.soujunior.domain.entities.auth.RegisterModel
import com.soujunior.domain.entities.auth.UserModel
import com.soujunior.domain.repository.AuthRepository
import retrofit2.awaitResponse

class AuthRepositoryImpl(
    private val service: Service,
    private val context: Context
    ) : AuthRepository {
    override suspend fun login(form: LoginModel): ApiResponseCode {
        if (!Util.statusInternet(context)) {
            throw Error("Erro na conexão com a internet!")
        }
        val deferredResponse = service.login(form).awaitResponse()
        return if (deferredResponse.isSuccessful) {
            ApiResponseCode(deferredResponse.code(), "Logado com sucesso")
        } else {
            val errorMessage = deferredResponse.errorBody()?.string() ?: "Erro desconhecido"
            ApiResponseCode(deferredResponse.code(), errorMessage)
        }
    }

    override suspend fun register(form: RegisterModel): ApiResponseCode {
        if (!Util.statusInternet(context)) {
            throw Error("Erro na conexão com a internet!")
        }
        val deferredResponse = service.register(form).awaitResponse()
        return if (deferredResponse.isSuccessful) {
            ApiResponseCode(deferredResponse.code(), "Registro bem sucedido")
        } else {
            val errorMessage = deferredResponse.errorBody()?.string() ?: "Erro desconhecido"
            ApiResponseCode(deferredResponse.code(), errorMessage)
        }
    }

    override suspend fun awaitingCode(form: AwaitingCodeModel): ApiResponseCode {
        if (!Util.statusInternet(context)) {
            throw Error("Erro na conexão com a internet!")
        }
        val deferredResponse = service.awaitingCode(form).awaitResponse()
        return if (deferredResponse.isSuccessful) {
            ApiResponseCode(deferredResponse.code(), "Sucesso, crie sua nova senha!")
        } else {
            val errorMessage = deferredResponse.errorBody()?.string() ?: "Erro desconhecido"
            ApiResponseCode(deferredResponse.code(), errorMessage)
        }
    }

    override suspend fun forgotPassword(form: ForgotPasswordModel): ApiResponseCode {
        if (!Util.statusInternet(context)) {
            throw Error("Erro na conexão com a internet!")
        }
        val deferredResponse = service.forgotPassword(form).awaitResponse()
        return if (deferredResponse.isSuccessful) {
            ApiResponseCode(deferredResponse.code(), "Codigo enviado para o email de cadastro!")
        } else {
            val errorMessage = deferredResponse.errorBody()?.string() ?: "Erro desconhecido"
            ApiResponseCode(deferredResponse.code(), errorMessage)
        }
    }

    override suspend fun changePassword(password: PasswordModel): ApiResponseCode {
        if (!Util.statusInternet(context)) {
            throw Error("Erro na conexão com a internet!")
        }
        val deferredResponse = service.changePassword(password).awaitResponse()
        return if (deferredResponse.isSuccessful) {
            ApiResponseCode(deferredResponse.code(), "Sucesso ao trocar senha!")
        } else {
            val errorMessage = deferredResponse.errorBody()?.string() ?: "Erro desconhecido"
            ApiResponseCode(deferredResponse.code(), errorMessage)
        }
    }



    override suspend fun home(name : UserModel): ApiResponseCode {
        if (!Util.statusInternet(context)) {
            throw Error("Erro na conexão com a internet!")
        }
        val deferredResponse = service.getUserName(name).awaitResponse()
        return if (deferredResponse.isSuccessful) {
            ApiResponseCode(deferredResponse.code(), "Olá $name")
        } else {
            val errorMessage = deferredResponse.errorBody()?.string() ?: "Erro desconhecido"
            ApiResponseCode(deferredResponse.code(), errorMessage)
        }
    }





}

