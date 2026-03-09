package com.soujunior.domain.use_case.pet

import com.soujunior.domain.mapper.Mapper.toDomain
import com.soujunior.domain.model.response.pet.SizeModel
import com.soujunior.domain.network.NetworkResult
import com.soujunior.domain.repository.GuardianRepository
import com.soujunior.domain.use_case.base.BaseUseCase
import com.soujunior.domain.use_case.base.DataResult

class GetListSizeUseCase(private val repository: GuardianRepository): BaseUseCase<String, List<SizeModel>>() {
    override suspend fun doWork(value: String): DataResult<List<SizeModel>> {
        return when (val response = repository.getListSize(value)) {
            is NetworkResult.Success -> { DataResult.Success(response.data.toDomain()) }
            is NetworkResult.Error -> DataResult.Failure(Throwable(message = "${response.code} -> ${response.body?.error}"))
            is NetworkResult.Exception -> DataResult.Failure(response.e)
        }
    }
}
