package com.soujunior.domain.repository

import com.soujunior.domain.entities.auth.UserModel


class UserRepository {

    fun getUserData(): UserModel {
        return UserModel("PAÇOCA")
    }

}