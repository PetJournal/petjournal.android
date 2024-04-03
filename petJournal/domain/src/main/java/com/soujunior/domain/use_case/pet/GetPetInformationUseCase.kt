package com.soujunior.domain.use_case.pet

import com.soujunior.domain.model.PetInformationModel
import com.soujunior.domain.repository.GuardianRepository
import com.soujunior.domain.use_case.base.BaseUseCase
import com.soujunior.domain.use_case.base.DataResult

class GetPetInformationUseCase(private val repository: GuardianRepository) : BaseUseCase<Long, PetInformationModel>() {
    override suspend fun doWork(value: Long): DataResult<PetInformationModel> {
        return try {
            val result = repository.getPetInformation(value)
            DataResult.Success(result)
        }catch (e: Exception){
            DataResult.Failure(e)
        }
    }
}