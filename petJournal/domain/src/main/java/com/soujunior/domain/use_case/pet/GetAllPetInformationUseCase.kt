package com.soujunior.domain.use_case.pet

import com.soujunior.domain.model.PetInformationModel
import com.soujunior.domain.model.response.pet_information.PetInformationItem
import com.soujunior.domain.network.NetworkResult
import com.soujunior.domain.repository.GuardianRepository
import com.soujunior.domain.use_case.base.BaseUseCase
import com.soujunior.domain.use_case.base.DataResult
import kotlinx.coroutines.flow.Flow
import java.lang.Exception

class GetAllPetInformationUseCase(private val repository: GuardianRepository) :
    BaseUseCase<Unit, List<PetInformationItem>>() {
    override suspend fun doWork(value: Unit): DataResult<List<PetInformationItem>> {
        return when (val response = repository.getAllPetInformation()) {
            is NetworkResult.Success -> {
                DataResult.Success(response.data)
            }

            is NetworkResult.Error -> DataResult.Failure(Throwable(message = "${response.code} -> ${response.body?.error}"))
            is NetworkResult.Exception -> DataResult.Failure(response.e)

        }
    }
}