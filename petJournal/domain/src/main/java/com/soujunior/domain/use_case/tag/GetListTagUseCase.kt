package com.soujunior.domain.use_case.tag

import com.soujunior.domain.mapper.Mapper.toDomain
import com.soujunior.domain.model.response.tag.TagModel
import com.soujunior.domain.network.NetworkResult
import com.soujunior.domain.repository.api.Repository
import com.soujunior.domain.use_case.base.BaseUseCase
import com.soujunior.domain.use_case.base.DataResult

class GetListTagUseCase(private val repository: Repository): BaseUseCase<Boolean, List<TagModel>>() {
    override suspend fun doWork(value: Boolean): DataResult<List<TagModel>> {
        return when (val response = repository.getListTag(value)) {
            is NetworkResult.Success -> { DataResult.Success(response.data.toDomain()) }
            is NetworkResult.Error -> DataResult.Failure(Throwable(message = "${response.code} -> ${response.body?.error}"))
            is NetworkResult.Exception -> DataResult.Failure(response.e)
        }
    }

    suspend fun executeLocalOnly(): DataResult<List<TagModel>> {
        return when (val response = repository.getListTag(forceRequest = false, localOnly = true)) {
            is NetworkResult.Success -> { DataResult.Success(response.data.toDomain()) }
            is NetworkResult.Error -> DataResult.Failure(Throwable(message = "${response.code} -> ${response.body?.error}"))
            is NetworkResult.Exception -> DataResult.Failure(response.e)
        }
    }
}
