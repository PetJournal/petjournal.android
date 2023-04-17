package com.soujunior.domain.usecase.auth

import com.gusoliveira.domain.usecase.base.BaseUseCase
import com.soujunior.domain.entities.auth.LoginModel
import com.soujunior.domain.repository.AuthRepository

class LoginUseCase (private val repository : AuthRepository) : BaseUseCase<LoginModel, String>(){

    override suspend fun doWork(value: LoginModel?): String {
        return if (value != null)
            when (repository.login(value)) {
                else -> "Erro ao fazer o Login!"
            } else "0"
    }

}