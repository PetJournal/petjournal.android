package com.soujunior.domain.usecase.auth

import android.util.Log
import com.gusoliveira.domain.usecase.base.BaseUseCase
import com.soujunior.domain.entities.auth.RegisterModel
import com.soujunior.domain.repository.AuthRepository

class RegisterUseCase(private val repository: AuthRepository) :
    BaseUseCase<RegisterModel, String>() {
    override suspend fun doWork(value: RegisterModel?): String {
        return if (value != null) {
            val result = repository.register(value)
            Log.e("testar", "$result")
            when (result.code) {
                in 100..199 -> "O servidor ainda esta processando a requisição"
                in 200..299 -> "Success"
                in 300..399 -> "Esta faltando alguma dado no formulário"
                in 400..499 -> "Erro do Client"
                in 500..599 -> "Erro ao processar requisição"
                else -> "Erro desconhecido"
            }
        } else "Houve um erro desconhecido!"
    }
}


/**
403 - email já existe
203 - sucesso ao cadastrar
400 - Falta parametro
 */
