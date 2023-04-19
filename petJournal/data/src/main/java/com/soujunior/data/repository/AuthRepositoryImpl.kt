package com.soujunior.data.repository

import android.content.Context
import com.soujunior.data.api.Service
import com.soujunior.data.util.Util
import com.soujunior.domain.entities.auth.ApiResponseCode
import com.soujunior.domain.entities.auth.LoginModel
import com.soujunior.domain.entities.auth.RegisterModel
import com.soujunior.domain.repository.AuthRepository
import retrofit2.awaitResponse

class AuthRepositoryImpl(
    private val service: Service,
    private val context: Context
    ) : AuthRepository {

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


}
