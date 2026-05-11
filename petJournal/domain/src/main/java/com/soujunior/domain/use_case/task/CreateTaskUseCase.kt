package com.soujunior.domain.use_case.task

import com.soujunior.domain.model.request.taskModels.TaskDTO
import com.soujunior.domain.network.NetworkResult
import com.soujunior.domain.repository.api.Repository
import com.soujunior.domain.use_case.base.BaseUseCase
import com.soujunior.domain.use_case.base.DataResult

data class CreateTaskParams(
    val task: TaskDTO,
    val sendToApi: Boolean
)

class CreateTaskUseCase(private val repository: Repository): BaseUseCase<CreateTaskParams, Unit>() {
    override suspend fun doWork(value: CreateTaskParams): DataResult<Unit> {
        val saveLocalResult = repository.saveTaskLocal(value.task)
        if (saveLocalResult is DataResult.Failure) {
            return saveLocalResult
        }

        if (!value.sendToApi) {
            return DataResult.Success(Unit)
        }

        return when (val response = repository.scheduled(value.task)) {
            is NetworkResult.Success -> { DataResult.Success(response.data) }
            is NetworkResult.Error -> DataResult.Failure(Throwable(message = "${response.code} -> ${response.body?.error}"))
            is NetworkResult.Exception -> DataResult.Failure(response.e)
        }
    }
}
