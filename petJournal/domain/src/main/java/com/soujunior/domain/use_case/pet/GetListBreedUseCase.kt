package com.soujunior.domain.use_case.pet

import com.soujunior.domain.model.response.pet.BreedModel
import com.soujunior.domain.model.response.pet.toDomain
import com.soujunior.domain.network.NetworkResult
import com.soujunior.domain.repository.GuardianRepository
import com.soujunior.domain.use_case.base.BaseUseCase
import com.soujunior.domain.use_case.base.DataResult

class GetListBreedUseCase(private val repository: GuardianRepository): BaseUseCase<String, List<BreedModel>>() {
    override suspend fun doWork(value: String): DataResult<List<BreedModel>> {
        return when (val response = repository.getListBreed(value)) {
            is NetworkResult.Success -> { DataResult.Success(response.data.toDomain()) }
            is NetworkResult.Error -> DataResult.Failure(Throwable(message = "${response.code} -> ${response.body?.error}"))
            is NetworkResult.Exception -> DataResult.Failure(response.e)
        }
    }
}
