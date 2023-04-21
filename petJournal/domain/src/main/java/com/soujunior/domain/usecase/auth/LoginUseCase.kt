package com.soujunior.domain.usecase.auth

import com.soujunior.domain.usecase.base.BaseUseCase
import com.soujunior.domain.entities.auth.LoginModel
import com.soujunior.domain.repository.AuthRepository

class LoginUseCase (
    private val repository : AuthRepository
    ) : BaseUseCase<LoginModel, String>(){
    override suspend fun doWork(value: LoginModel?): String {
        return if (value != null) {
            val result = repository.login(value)
            when (result.code) {
                in 100..199 -> "O servidor ainda esta processando a requisição"
                in 200..299 -> "Success"
                in 300..399 -> throw Error("Esta faltando alguma dado no formulário")
                in 400..499 -> throw Error("Erro do Client")
                in 500..599 -> throw Error("Erro ao processar requisição")
                else -> throw Error("Erro desconhecido")
            }
        } else throw Error("Houve um erro desconhecido!")
    }
}