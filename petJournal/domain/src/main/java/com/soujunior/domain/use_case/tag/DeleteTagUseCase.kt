package com.soujunior.domain.use_case.tag

import com.soujunior.domain.network.NetworkResult
import com.soujunior.domain.repository.api.Repository
import com.soujunior.domain.use_case.base.BaseUseCase
import com.soujunior.domain.use_case.base.DataResult

class DeleteTagUseCase(private val repository: Repository): BaseUseCase<String, Unit>() {
    override suspend fun doWork(value: String): DataResult<Unit> {
        return when (val response = repository.deleteTag(value)) {
            is NetworkResult.Success -> { DataResult.Success(Unit) }
            is NetworkResult.Error -> DataResult.Failure(Throwable(message = "${response.code} -> ${response.body?.error}"))
            is NetworkResult.Exception -> DataResult.Failure(response.e)
        }
    }
}
