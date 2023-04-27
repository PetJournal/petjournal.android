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
                in 100..199 -> "The server is still processing the request."
                in 200..299 -> "Success"
                in 300..399 -> throw Error("There is some data missing from the form")
                in 400..499 -> throw Error("Error Client")
                in 500..599 -> throw Error("Error processing request")
                else -> throw Error("Unknown error")
            }
        } else throw Error("Unknown error!")
    }
}