package com.soujunior.domain.repository

import com.soujunior.domain.entities.auth.ApiResponseCode
import com.soujunior.domain.entities.auth.RegisterModel

interface AuthRepository {
    suspend fun register(form : RegisterModel) : ApiResponseCode
}