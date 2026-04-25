package com.soujunior.domain.use_case.task

import com.soujunior.domain.mapper.Mapper.toDomain
import com.soujunior.domain.model.taskModel.PaginatedScheduleResponseModel
import com.soujunior.domain.network.NetworkResult
import com.soujunior.domain.repository.api.Repository
import com.soujunior.domain.use_case.base.BaseUseCase
import com.soujunior.domain.use_case.base.DataResult
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class GetListCurrentMonthTaskUseCase(private val repository: Repository):
    BaseUseCase<Boolean, PaginatedScheduleResponseModel>() {
    override suspend fun doWork(value: Boolean): DataResult<PaginatedScheduleResponseModel> {
        val today = LocalDate.now()
        val startDate = today.withDayOfMonth(1).atStartOfDay().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        val endDate = today.withDayOfMonth(today.lengthOfMonth()).atTime(LocalTime.MAX).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)

        return when (val response = repository.listTasksByPeriod(startDate, endDate, value)) {
            is NetworkResult.Success -> { DataResult.Success(response.data.toDomain()) }
            is NetworkResult.Error -> DataResult.Failure(Throwable(message = "${response.code} -> ${response.body?.error}"))
            is NetworkResult.Exception -> DataResult.Failure(response.e)
        }
    }
}
