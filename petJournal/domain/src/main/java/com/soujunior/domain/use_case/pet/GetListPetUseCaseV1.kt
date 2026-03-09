package com.soujunior.domain.use_case.pet

import com.soujunior.domain.mapper.Mapper.toPetModelList
import com.soujunior.domain.model.PetModel
import com.soujunior.domain.network.NetworkResult
import com.soujunior.domain.repository.GuardianRepository
import com.soujunior.domain.use_case.base.BaseUseCase
import com.soujunior.domain.use_case.base.DataResult

class GetListPetUseCaseV1(private val repository: GuardianRepository):
    BaseUseCase<Unit, List<PetModel>>() {
    override suspend fun doWork(value: Unit): DataResult<List<PetModel>> {
        return when (val response = repository.getListPet()) {
            is NetworkResult.Success -> { DataResult.Success(response.data.toPetModelList()) }
            is NetworkResult.Error -> DataResult.Failure(Throwable(message = "${response.code} -> ${response.body?.error}"))
            is NetworkResult.Exception -> DataResult.Failure(response.e)
        }
    }
}
