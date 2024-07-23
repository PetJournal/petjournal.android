package com.soujunior.domain.use_case.pet

import com.soujunior.domain.model.PetInformationModel
import com.soujunior.domain.repository.GuardianRepository
import com.soujunior.domain.use_case.base.BaseUseCase
import com.soujunior.domain.use_case.base.DataResult
import kotlinx.coroutines.flow.Flow
import java.lang.Exception

class GetAllPetInformationUseCase(private val repository: GuardianRepository):
    BaseUseCase<Unit, Flow<List<PetInformationModel>>>() {
    override suspend fun doWork(value: Unit): DataResult<Flow<List<PetInformationModel>>> {
        return try {
            val result = repository.getAllPetInformation()
            DataResult.Success(result.success.data)
        }catch (e: Exception){
            DataResult.Failure(e)
        }
    }

}