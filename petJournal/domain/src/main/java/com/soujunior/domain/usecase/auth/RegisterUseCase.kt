package com.soujunior.domain.usecase.auth

import com.gusoliveira.domain.usecase.base.BaseUseCase
import com.soujunior.domain.entities.auth.RegisterModel
import com.soujunior.domain.repository.AuthRepository

class RegisterUseCase (private val repository : AuthRepository) : BaseUseCase<RegisterModel, String>(){
    override suspend fun doWork(value: RegisterModel?): String {
        return if (value != null)
            when (repository.register(value)) {
                else -> "Erro ao cadastrar usuário!"
            } else "0"
    }
}

/**
403 - email já existe
203 - sucesso ao cadastrar
400 - Falta parametro
 */
