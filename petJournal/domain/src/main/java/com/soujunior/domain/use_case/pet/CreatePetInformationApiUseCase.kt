package com.soujunior.domain.use_case.pet

import com.soujunior.domain.model.PetModel
import com.soujunior.domain.network.NetworkResult
import com.soujunior.domain.repository.GuardianRepository
import com.soujunior.domain.use_case.base.BaseUseCase
import com.soujunior.domain.use_case.base.DataResult

class CreatePetInformationApiUseCase(private val repository: GuardianRepository) :
    BaseUseCase<PetModel, Unit>() {
    override suspend fun doWork(value: PetModel): DataResult<Unit> {
        return try {
            return when (val response = repository.createPet(value)) {
                is NetworkResult.Success -> { DataResult.Success(Unit) }
                is NetworkResult.Error -> DataResult.Failure(Throwable(message = "${response.code} -> ${response.body?.error}"))
                is NetworkResult.Exception -> DataResult.Failure(response.e)
            }
        } catch (e: Throwable) {
            DataResult.Failure(e)
        }
    }
}
