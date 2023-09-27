package com.soujunior.data.repository

import android.content.Context
import com.soujunior.data.remote.GuardianService
import com.soujunior.data.util.manager.JwtManager
import com.soujunior.domain.model.response.GuardianNameResponse
import com.soujunior.domain.network.NetworkResult
import com.soujunior.domain.repository.GuardianRepository

class GuardianRepositoryImpl(
    private val guardianApi: GuardianService,
    private val context: Context
): GuardianRepository {

    private val jwtManager: JwtManager = JwtManager.getInstance(context)
    override suspend fun getGuardianName(): NetworkResult<GuardianNameResponse> {
        val token = "Bearer " + jwtManager.getToken()
        return guardianApi.getGuardianName(token)
    }

}
