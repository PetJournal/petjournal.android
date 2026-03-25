package com.soujunior.domain.use_case.task

import com.soujunior.domain.model.request.taskModels.TaskDTO
import com.soujunior.domain.network.NetworkResult
import com.soujunior.domain.repository.GuardianRepository
import com.soujunior.domain.use_case.base.BaseUseCase
import com.soujunior.domain.use_case.base.DataResult

class CreateTaskUseCase(private val repository: GuardianRepository): BaseUseCase<TaskDTO, Unit>() {
    override suspend fun doWork(value: TaskDTO): DataResult<Unit> {
        return when (val response = repository.scheduled(value)) {
            is NetworkResult.Success -> { DataResult.Success(response.data) }
            is NetworkResult.Error -> DataResult.Failure(Throwable(message = "${response.code} -> ${response.body?.error}"))
            is NetworkResult.Exception -> DataResult.Failure(response.e)
        }
    }
}
