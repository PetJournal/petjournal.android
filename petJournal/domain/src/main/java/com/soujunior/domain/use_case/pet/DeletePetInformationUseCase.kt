package com.soujunior.domain.use_case.pet

import com.soujunior.domain.repository.GuardianRepository
import com.soujunior.domain.use_case.base.BaseUseCase
import com.soujunior.domain.use_case.base.DataResult

class DeletePetInformationUseCase(private val repository: GuardianRepository): BaseUseCase<Long, Unit>(){
    override suspend fun doWork(value: Long): DataResult<Unit> {
        return try {
            val result = repository.deletePetInformation(value)
            DataResult.Success(result.success.data)
        } catch (e: Exception) {
            DataResult.Failure(e)
        }
    }

}