package com.soujunior.domain.use_case.guardian

import com.soujunior.domain.model.response.GuardianNameResponse
import com.soujunior.domain.network.NetworkResult
import com.soujunior.domain.repository.GuardianRepository
import com.soujunior.domain.use_case.base.BaseUseCase
import com.soujunior.domain.use_case.base.DataResult

class GetGuardianNameUseCase(private val repository: GuardianRepository) :
    BaseUseCase<Unit, GuardianNameResponse>() {
    override suspend fun doWork(value: Unit): DataResult<GuardianNameResponse> {
        return when (val response = repository.getGuardianName()) {
            is NetworkResult.Success -> DataResult.Success(response.data)
            is NetworkResult.Error -> DataResult.Failure(Throwable(message = "${response.code} -> ${response.body?.error}"))
            is NetworkResult.Exception -> DataResult.Failure(response.e)
        }
    }
}