package com.soujunior.domain.usecase.auth


import com.soujunior.domain.entities.auth.ForgotPasswordModel

import com.soujunior.domain.repository.AuthRepository
import com.soujunior.domain.usecase.base.BaseUseCase


class ForgotPasswordCase(
        private val repository: AuthRepository
    ) : BaseUseCase<ForgotPasswordModel, String>() {
        override suspend fun doWork(value: ForgotPasswordModel?): String {
            return if (value != null) {
                val result = repository.forgotPassword (value)
                when (result.code) {
                    in 100..199 -> "The server is still processing the request."
                    in 200..299 -> "Success"
                    in 300..399 -> throw Error("Esta faltando alguma dado no formulário")
                    in 400..499 -> throw Error("Erro do Client")
                    in 500..599 -> throw Error("Erro ao processar requisição")
                    else -> throw Error("Erro desconhecido")
                }
            } else throw Error("Houve um erro desconhecido!")
        }

}

