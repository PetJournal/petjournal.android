package com.soujunior.domain.use_case.task

import android.content.ContentValues.TAG
import android.util.Log
import com.soujunior.domain.model.request.taskModels.TaskDTO
import com.soujunior.domain.model.taskModel.PaginatedScheduleResponseDTO
import com.soujunior.domain.network.NetworkResult
import com.soujunior.domain.repository.GuardianRepository
import com.soujunior.domain.use_case.base.BaseUseCase
import com.soujunior.domain.use_case.base.DataResult

class GetListCurrentWeekTaskUseCase(private val repository: GuardianRepository): BaseUseCase<Unit, PaginatedScheduleResponseDTO>() {
    override suspend fun doWork(value: Unit): DataResult<PaginatedScheduleResponseDTO> {
        return when (val response = repository.listCurrentWeekScheduled()) {
            is NetworkResult.Success -> {
            Log.e(TAG, "GetListCurrentWeekTaskUseCase: ${response.data}")
                DataResult.Success(response.data)
            }
            is NetworkResult.Error -> DataResult.Failure(Throwable(message = "${response.code} -> ${response.body?.error}"))
            is NetworkResult.Exception -> DataResult.Failure(response.e)
        }
    }
}
