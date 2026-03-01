package com.soujunior.domain.use_case.task

import com.soujunior.domain.model.response.tag.TagModel
import com.soujunior.domain.model.response.tag.UpdatePetByIdDTO
import com.soujunior.domain.model.response.tag.toDTO
import com.soujunior.domain.model.response.tag.toDomain
import com.soujunior.domain.network.NetworkResult
import com.soujunior.domain.repository.GuardianRepository
import com.soujunior.domain.use_case.base.BaseUseCase
import com.soujunior.domain.use_case.base.DataResult

class DeleteTagUseCase(private val repository: GuardianRepository): BaseUseCase<String, Unit>() {
    override suspend fun doWork(value: String): DataResult<Unit> {
        return when (val response = repository.deleteTag(value)) {
            is NetworkResult.Success -> { DataResult.Success(Unit) }
            is NetworkResult.Error -> DataResult.Failure(Throwable(message = "${response.code} -> ${response.body?.error}"))
            is NetworkResult.Exception -> DataResult.Failure(response.e)
        }
    }
}
