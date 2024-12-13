package com.soujunior.domain.use_case.pet

import com.soujunior.domain.model.response.PetInformationDeleted
import com.soujunior.domain.network.NetworkResult
import com.soujunior.domain.repository.GuardianRepository
import com.soujunior.domain.use_case.base.BaseUseCase
import com.soujunior.domain.use_case.base.DataResult

class DeletePetInformationUseCase(private val repository: GuardianRepository) :
    BaseUseCase<Long, Unit>() {
    override suspend fun doWork(value: Long): DataResult<Unit> {
        return when (val response = repository.deletePetInformation(value)) {
            is NetworkResult.Error -> DataResult.Failure(Throwable(message = "${response.code} -> ${response.body?.error}"))
            is NetworkResult.Exception -> DataResult.Failure(response.e)
            is NetworkResult.Success -> {
                DataResult.Success(response.data as Unit)
            }
        }
    }

}