package com.soujunior.domain.use_case.pet

import com.soujunior.domain.model.PetInformationModel
import com.soujunior.domain.repository.GuardianRepository
import com.soujunior.domain.use_case.base.BaseUseCase
import com.soujunior.domain.use_case.base.DataResult

class SavePetInformationUseCase(private val repository: GuardianRepository) : BaseUseCase<PetInformationModel, Long>() {
    override suspend fun doWork(value: PetInformationModel): DataResult<Long> {
        return try {
            val result = repository.savePetInformation(value)
            DataResult.Success(result.success.data)
        }catch (e: Throwable){
            DataResult.Failure(e)
        }
    }

}
