package com.soujunior.domain.use_case.pet

import com.soujunior.domain.model.PetModel
import com.soujunior.domain.repository.GuardianRepository
import com.soujunior.domain.use_case.base.BaseUseCase
import com.soujunior.domain.use_case.base.DataResult

class UpdatePetInformationUseCase(private val repository: GuardianRepository) :
    BaseUseCase<PetModel, Unit>() {
    override suspend fun doWork(value: PetModel): DataResult<Unit> {
        return try {
            val result = repository.updatePet(value)
            DataResult.Success(result.success.data)
        } catch (e: Throwable) {
            DataResult.Failure(e)
        }
    }
}
