package com.soujunior.domain.repository

import com.soujunior.domain.model.response.GuardianNameResponse
import com.soujunior.domain.network.NetworkResult

interface GuardianRepository {
    suspend fun getGuardianName(): NetworkResult<GuardianNameResponse>
}