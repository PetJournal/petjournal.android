package com.soujunior.domain.use_case.pet

import com.soujunior.domain.model.PetInformationModel
import com.soujunior.domain.repository.GuardianRepository
import com.soujunior.domain.use_case.base.BaseUseCase
import com.soujunior.domain.use_case.base.DataResult
import java.lang.Exception

class GetAllPetInformationUseCase(private val repository: GuardianRepository):
    BaseUseCase<Unit, List<PetInformationModel>>() {
    override suspend fun doWork(value: Unit): DataResult<List<PetInformationModel>> {
        return try {
            val result = repository.getAllPetInformation()
            DataResult.Success(result.success.data)
        }catch (e: Exception){
            DataResult.Failure(e)
        }
    }

}