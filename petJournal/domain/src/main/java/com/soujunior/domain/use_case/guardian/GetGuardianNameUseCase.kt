package com.soujunior.domain.use_case.guardian

import com.soujunior.domain.model.response.GuardianNameResponse
import com.soujunior.domain.network.NetworkResult
import com.soujunior.domain.repository.api.Repository
import com.soujunior.domain.use_case.base.BaseUseCase
import com.soujunior.domain.use_case.base.DataResult

class GetGuardianNameUseCase(private val repository: Repository) :
    BaseUseCase<Boolean, GuardianNameResponse>() {
    override suspend fun doWork(value: Boolean): DataResult<GuardianNameResponse> {
        return when (val response = repository.getGuardianName(value)) {
            is NetworkResult.Success -> { DataResult.Success(response.data) }
            is NetworkResult.Error -> DataResult.Failure(Throwable(message = "${response.code} -> ${response.body?.error}"))
            is NetworkResult.Exception -> DataResult.Failure(response.e)
        }
    }
}
