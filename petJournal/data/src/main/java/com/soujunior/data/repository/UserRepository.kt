package com.soujunior.data.repository

import kotlinx.coroutines.delay

data class UserData(
    val userName : String
)

class UserRepository {
    //todo: Configurar trazendo informação do nome do usuario do backend.
    fun getUserData(): UserData {
         return UserData("Gelson")
    }
}