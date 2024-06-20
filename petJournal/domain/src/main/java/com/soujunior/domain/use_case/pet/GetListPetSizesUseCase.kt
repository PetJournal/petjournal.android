package com.soujunior.domain.use_case.pet

import com.soujunior.domain.model.request.PetSizeItemModel
import com.soujunior.domain.network.NetworkResult
import com.soujunior.domain.repository.GuardianRepository
import com.soujunior.domain.use_case.base.BaseUseCase
import com.soujunior.domain.use_case.base.DataResult

class GetListPetSizesUseCase(private val repository: GuardianRepository): BaseUseCase<String, List<PetSizeItemModel>>() {
    override suspend fun doWork(value: String): DataResult<List<PetSizeItemModel>> {
        return when (val response = repository.getListPetSizes(value)) {
            is NetworkResult.Success -> { DataResult.Success(response.data) }
            is NetworkResult.Error -> DataResult.Failure(Throwable(message = "${response.code} -> ${response.body?.error}"))
            is NetworkResult.Exception -> DataResult.Failure(response.e)
        }
    }
}