package com.soujunior.domain.use_case.task

import com.soujunior.domain.model.response.tag.TagModel
import com.soujunior.domain.model.response.tag.toDTO
import com.soujunior.domain.model.response.tag.toDomain
import com.soujunior.domain.network.NetworkResult
import com.soujunior.domain.repository.GuardianRepository
import com.soujunior.domain.use_case.base.BaseUseCase
import com.soujunior.domain.use_case.base.DataResult

class CreateTagUseCase(private val repository: GuardianRepository): BaseUseCase<TagModel, TagModel>() {
    override suspend fun doWork(value: TagModel): DataResult<TagModel> {
        return when (val response = repository.createTag(value.toDTO())) {
            is NetworkResult.Success -> { DataResult.Success(response.data.toDomain()) }
            is NetworkResult.Error -> DataResult.Failure(Throwable(message = "${response.code} -> ${response.body?.error}"))
            is NetworkResult.Exception -> DataResult.Failure(response.e)
        }
    }
}
