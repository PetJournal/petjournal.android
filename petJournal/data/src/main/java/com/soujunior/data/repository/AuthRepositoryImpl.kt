package com.soujunior.data.repository

import android.content.Context
import android.util.Log
import com.soujunior.data.api.Service
import com.soujunior.data.util.Util
import com.soujunior.domain.entities.auth.ApiResponseCode
import com.soujunior.domain.entities.auth.RegisterModel
import com.soujunior.domain.exceptions.NoConnection
import com.soujunior.domain.repository.AuthRepository
import retrofit2.awaitResponse

class AuthRepositoryImpl(private val service: Service, private val context: Context) : AuthRepository {
    override suspend fun register(form: RegisterModel): ApiResponseCode {
        if(Util.statusInternet(context = context)) {
            val deferredResponse = service.register(form).awaitResponse()
            return if (deferredResponse.code() in 200..299) {
                ApiResponseCode(deferredResponse.code(), "Registro bem sucedido")
            } else {
                val errorBody = deferredResponse.errorBody()?.string()
                val errorMessage = errorBody ?: "Erro desconhecido"
                ApiResponseCode(deferredResponse.code(), errorMessage)
            }
        }
        else{
            throw NoConnection("Erro na conexão com a internet!")
        }
    }
}
