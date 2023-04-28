package com.soujunior.domain.usecase.auth

import com.soujunior.domain.entities.auth.AwaitingCodeModel
import com.soujunior.domain.repository.AuthRepository
import com.soujunior.domain.usecase.base.BaseUseCase


class AwaitingCodeUseCase (
    private val repository : AuthRepository
) : BaseUseCase<AwaitingCodeModel, String>(){
    override suspend fun doWork(value: AwaitingCodeModel?): String {
        return if (value != null) {
            val result = repository.awaitingCode(value)
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