package com.soujunior.domain.use_case.pet

import com.soujunior.domain.mapper.Mapper.toPetModelListV2
import com.soujunior.domain.model.PetModelV2
import com.soujunior.domain.network.NetworkResult
import com.soujunior.domain.repository.api.Repository
import com.soujunior.domain.use_case.base.BaseUseCase
import com.soujunior.domain.use_case.base.DataResult

class GetListPetUseCaseV2(private val repository: Repository):
    BaseUseCase<Unit, List<PetModelV2>>() {
    override suspend fun doWork(value: Unit): DataResult<List<PetModelV2>> {
        return when (val response = repository.getListPet()) {
            is NetworkResult.Success -> { DataResult.Success(response.data.toPetModelListV2()) }
            is NetworkResult.Error -> DataResult.Failure(Throwable(message = "${response.code} -> ${response.body?.error}"))
            is NetworkResult.Exception -> DataResult.Failure(response.e)
        }
    }
}
