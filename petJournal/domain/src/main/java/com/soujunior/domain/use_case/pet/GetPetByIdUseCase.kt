package com.soujunior.domain.use_case.pet

import com.soujunior.domain.model.PetDetailsDTO
import com.soujunior.domain.network.NetworkResult
import com.soujunior.domain.repository.api.Repository
import com.soujunior.domain.use_case.base.BaseUseCase
import com.soujunior.domain.use_case.base.DataResult

class GetPetByIdUseCase( private val repository: Repository ) : BaseUseCase<String, PetDetailsDTO>() {
    override suspend fun doWork(value: String): DataResult<PetDetailsDTO> {
        return try {
            val response = repository.getPetById(value)
            when (response) {
                is NetworkResult.Success -> { DataResult.Success(response.data) }
                is NetworkResult.Error -> {
                    DataResult.Failure(
                        Throwable(message = "${response.code} -> ${response.body?.error}")
                    )
                }
                is NetworkResult.Exception -> {
                    DataResult.Failure(response.e)
                }
            }
        } catch (e: Throwable) {
            DataResult.Failure(e)
        }
    }
}
