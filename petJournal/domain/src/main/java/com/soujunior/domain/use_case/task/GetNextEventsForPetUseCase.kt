package com.soujunior.domain.use_case.task

import com.soujunior.domain.mapper.Mapper.toDomain
import com.soujunior.domain.model.taskModel.PaginatedNextEventsResponseModel
import com.soujunior.domain.network.NetworkResult
import com.soujunior.domain.repository.api.Repository
import com.soujunior.domain.use_case.base.BaseUseCase
import com.soujunior.domain.use_case.base.DataResult

data class GetNextEventsForPetParams(
    val petId: String,
    val forceRequest: Boolean = false
)

class GetNextEventsForPetUseCase(private val repository: Repository):
    BaseUseCase<GetNextEventsForPetParams, PaginatedNextEventsResponseModel>() {
    override suspend fun doWork(value: GetNextEventsForPetParams): DataResult<PaginatedNextEventsResponseModel> {
        return when (val response = repository.getNextEventsForPet(value.petId, value.forceRequest)) {
            is NetworkResult.Success -> { DataResult.Success(response.data.toDomain()) }
            is NetworkResult.Error -> DataResult.Failure(Throwable(message = "${response.code} -> ${response.body?.error}"))
            is NetworkResult.Exception -> DataResult.Failure(response.e)
        }
    }
}
