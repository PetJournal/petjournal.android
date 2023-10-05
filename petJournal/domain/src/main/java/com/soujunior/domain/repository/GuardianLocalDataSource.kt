package com.soujunior.domain.repository

import com.soujunior.domain.model.response.GuardianNameResponse

interface GuardianLocalDataSource {
    suspend fun getGuardianName(): String?
    suspend fun saveGuardianName(response: GuardianNameResponse)
}