package com.soujunior.domain.use_case.task

import com.soujunior.domain.mapper.Mapper.toDomain
import com.soujunior.domain.model.taskModel.PaginatedScheduleResponseModel
import com.soujunior.domain.network.NetworkResult
import com.soujunior.domain.repository.GuardianRepository
import com.soujunior.domain.use_case.base.BaseUseCase
import com.soujunior.domain.use_case.base.DataResult

class GetListCurrentWeekTaskUseCase(private val repository: GuardianRepository):
    BaseUseCase<Unit, PaginatedScheduleResponseModel>() {
    override suspend fun doWork(value: Unit): DataResult<PaginatedScheduleResponseModel> {
        return when (val response = repository.listCurrentWeekScheduled()) {
            is NetworkResult.Success -> { DataResult.Success(response.data.toDomain()) }
            is NetworkResult.Error -> DataResult.Failure(Throwable(message = "${response.code} -> ${response.body?.error}"))
            is NetworkResult.Exception -> DataResult.Failure(response.e)
        }
    }
}
