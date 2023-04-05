package com.soujunior.data.repository

import com.soujunior.data.api.Service
import com.soujunior.domain.entities.auth.ApiResponseCode
import com.soujunior.domain.entities.auth.RegisterModel
import com.soujunior.domain.repository.AuthRepository
import retrofit2.awaitResponse

class AuthRepositoryImpl(private val service: Service) : AuthRepository {
    override suspend fun register(form: RegisterModel): ApiResponseCode {
        val deferredResponse = service.register(form).awaitResponse()
        return if (deferredResponse.isSuccessful) {
            ApiResponseCode(deferredResponse.code(), "Registro bem sucedido")
        } else {
            val errorBody = deferredResponse.errorBody()?.string()
            val errorMessage = errorBody ?: "Erro desconhecido"
            ApiResponseCode(deferredResponse.code(), errorMessage)
        }
    }
}
