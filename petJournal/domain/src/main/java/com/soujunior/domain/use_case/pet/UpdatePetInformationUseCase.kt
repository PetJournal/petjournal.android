package com.soujunior.domain.use_case.pet

import com.soujunior.domain.model.PetInformationModel
import com.soujunior.domain.repository.GuardianRepository
import com.soujunior.domain.use_case.base.BaseUseCase
import com.soujunior.domain.use_case.base.DataResult

class UpdatePetInformationUseCase(private val repository: GuardianRepository) : BaseUseCase<PetInformationModel, Unit>() {
    override suspend fun doWork(value: PetInformationModel): DataResult<Unit> {
        return try {
            val result = repository.updatePetInformation(value)
            DataResult.Success(result.success.data)
        }catch (e: Throwable){
            DataResult.Failure(e)
        }


    }


}