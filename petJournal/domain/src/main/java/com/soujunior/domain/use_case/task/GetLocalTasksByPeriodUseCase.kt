package com.soujunior.domain.use_case.task

import com.soujunior.domain.mapper.Mapper.toDomain
import com.soujunior.domain.model.taskModel.PaginatedScheduleResponseModel
import com.soujunior.domain.repository.api.Repository
import com.soujunior.domain.use_case.base.BaseUseCase
import com.soujunior.domain.use_case.base.DataResult

class GetLocalTasksByPeriodUseCase(private val repository: Repository) :
    BaseUseCase<GetLocalTasksByPeriodUseCase.Input, PaginatedScheduleResponseModel>() {

    data class Input(
        val startAt: String,
        val endAt: String,
        val considerTime: Boolean
    )

    override suspend fun doWork(value: Input): DataResult<PaginatedScheduleResponseModel> {
        return when (val response = repository.getLocalTasksByPeriod(value.startAt, value.endAt, value.considerTime)) {
            is DataResult.Success -> {
                DataResult.Success(response.data.toDomain())
            }
            is DataResult.Failure -> {
                DataResult.Failure(response.throwable)
            }
        }
    }
}
