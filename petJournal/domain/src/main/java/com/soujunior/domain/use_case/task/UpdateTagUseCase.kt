package com.soujunior.domain.use_case.task

import com.soujunior.domain.model.response.tag.TagModel
import com.soujunior.domain.model.response.tag.toDTO
import com.soujunior.domain.network.NetworkResult
import com.soujunior.domain.repository.GuardianRepository
import com.soujunior.domain.use_case.base.BaseUseCase
import com.soujunior.domain.use_case.base.DataResult

class UpdateTagUseCase(private val repository: GuardianRepository): BaseUseCase<Pair<String, TagModel>, Unit>() {
    override suspend fun doWork(value: Pair<String, TagModel>): DataResult<Unit> {
        return when (val response = repository.updateTag(value.second.toDTO().copy(id = value.first))) {
            is NetworkResult.Success -> {
                DataResult.Success(Unit) }
            is NetworkResult.Error -> DataResult.Failure(Throwable(message = "${response.code} -> ${response.body?.error}"))
            is NetworkResult.Exception -> DataResult.Failure(response.e)
        }
    }
}
