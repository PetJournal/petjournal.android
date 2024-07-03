package com.soujunior.domain.use_case.pet

import com.soujunior.domain.model.PetInformationModel
import com.soujunior.domain.network.NetworkResult
import com.soujunior.domain.repository.GuardianRepository
import com.soujunior.domain.use_case.base.BaseUseCase
import com.soujunior.domain.use_case.base.DataResult

class CreatePetInformationApiUseCase(private val repository: GuardianRepository) :
    BaseUseCase<PetInformationModel, Unit>() {
    override suspend fun doWork(value: PetInformationModel): DataResult<Unit> {
        return try {
            return when (val response = repository.createPetInformationApi(value)) {
                is NetworkResult.Success -> { DataResult.Success(response.data) }
                is NetworkResult.Error -> DataResult.Failure(Throwable(message = "${response.code} -> ${response.body?.error}"))
                is NetworkResult.Exception -> DataResult.Failure(response.e)
            }


        } catch (e: Throwable) {
            DataResult.Failure(e)
        }
    }
}
