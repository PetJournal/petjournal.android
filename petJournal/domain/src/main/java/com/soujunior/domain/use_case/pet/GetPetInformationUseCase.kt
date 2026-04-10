package com.soujunior.domain.use_case.pet

import com.soujunior.domain.model.PetModel
import com.soujunior.domain.repository.api.Repository
import com.soujunior.domain.use_case.base.BaseUseCase
import com.soujunior.domain.use_case.base.DataResult

class GetPetInformationUseCase(private val repository: Repository) :
    BaseUseCase<Long, PetModel>() {
    override suspend fun doWork(value: Long): DataResult<PetModel> {
        return try {
            val result = repository.getPet(value)
            DataResult.Success(result.success.data)
        } catch (e: Exception) {
            DataResult.Failure(e)
        }
    }
}
