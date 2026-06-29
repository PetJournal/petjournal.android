package com.soujunior.domain.use_case.task

import com.soujunior.domain.network.NetworkResult
import com.soujunior.domain.repository.api.Repository
import com.soujunior.domain.use_case.base.BaseUseCase
import com.soujunior.domain.use_case.base.DataResult

class DeleteTasksByIdUseCase(private val repository: Repository ) : BaseUseCase<String, Unit>() {
    override suspend fun doWork(value: String): DataResult<Unit> {
        return try {
            when (val response = repository.deleteTasksById(value)) {
                is NetworkResult.Success -> { DataResult.Success(response.data) }
                is NetworkResult.Error -> {
                    DataResult.Failure(
                        Throwable(message = "${response.code} -> ${response.body?.error}")
                    )
                }
                is NetworkResult.Exception -> {
                    DataResult.Failure(response.e)
                }
            }
        } catch (e: Throwable) {
            DataResult.Failure(e)
        }
    }
}
