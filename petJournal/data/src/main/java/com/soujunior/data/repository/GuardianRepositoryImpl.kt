package com.soujunior.data.repository

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import com.soujunior.data.remote.GuardianService
import com.soujunior.data.util.manager.JwtManager
import com.soujunior.domain.model.response.GuardianNameResponse
import com.soujunior.domain.network.NetworkResult
import com.soujunior.domain.repository.GuardianLocalDataSource
import com.soujunior.domain.repository.GuardianRepository
import kotlinx.coroutines.coroutineScope

class GuardianRepositoryImpl(
    private val guardianApi: GuardianService,
    private val guardianLocalDataSourceImpl: GuardianLocalDataSource,
    private val context: Context
) : GuardianRepository {

    private val jwtManager: JwtManager = JwtManager.getInstance(context)
    override suspend fun getGuardianName(): NetworkResult<GuardianNameResponse> {
        val localName = guardianLocalDataSourceImpl.getGuardianName()
        return if (localName != null) {
            NetworkResult.Success(GuardianNameResponse(localName, ""))
        } else {
            val token = "Bearer " + jwtManager.getToken()
            when (val apiResult = guardianApi.getGuardianName(token)) {
                is NetworkResult.Success -> {
                    coroutineScope {
                        try {
                            guardianLocalDataSourceImpl.saveGuardianName(apiResult.data)
                        } catch (e: Exception) {
                            Log.e(TAG, "Exeption: " + e.message)
                        }
                    }
                    NetworkResult.Success(GuardianNameResponse(apiResult.data.firstName, ""))
                }

                else -> apiResult
            }
        }
    }
}
