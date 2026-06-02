package com.soujunior.domain.use_case.pet

import com.soujunior.domain.model.PetModel
import com.soujunior.domain.repository.api.Repository
import com.soujunior.domain.use_case.base.BaseUseCase
import com.soujunior.domain.use_case.base.DataResult

class SavePetInformationUseCase(private val repository: Repository) :
    BaseUseCase<PetModel, Long>() {
    override suspend fun doWork(value: PetModel): DataResult<Long> {
        return try {
            val result = repository.savePet(value)
            DataResult.Success(result.success.data)
        } catch (e: Throwable) {
            DataResult.Failure(e)
        }
    }
}
