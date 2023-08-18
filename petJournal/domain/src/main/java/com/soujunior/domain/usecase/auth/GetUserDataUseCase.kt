package com.soujunior.domain.usecase.auth

import com.soujunior.domain.entities.auth.LoginModel
import com.soujunior.domain.entities.auth.UserModel
import com.soujunior.domain.repository.AuthRepository
import com.soujunior.domain.repository.UserRepository
import com.soujunior.domain.usecase.base.BaseUseCase


class GetUserDataUseCase (
    private val repository : AuthRepository,
    private val userRepository: UserRepository) : BaseUseCase<UserModel, String>(){

    operator fun invoke(): UserModel {

        return userRepository.getUserData()

    }


    override suspend fun doWork(value: UserModel?): String {
        return if (value != null) {
            val result = repository.home(value)
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