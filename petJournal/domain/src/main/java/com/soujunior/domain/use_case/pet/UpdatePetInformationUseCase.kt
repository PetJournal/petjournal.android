package com.soujunior.domain.use_case.pet

import com.soujunior.domain.mapper.Mapper.toDTO
import com.soujunior.domain.model.PetModel
import com.soujunior.domain.network.NetworkResult
import com.soujunior.domain.repository.api.Repository
import com.soujunior.domain.use_case.base.BaseUseCase
import com.soujunior.domain.use_case.base.DataResult

class UpdatePetInformationUseCase(private val repository: Repository) :
    BaseUseCase<Pair<String, PetModel>, Unit>() {
    override suspend fun doWork(value: Pair<String, PetModel>): DataResult<Unit> {
        return try {
            val response = repository.updatePet(
                id = value.first,
                pet = value.second.toDTO(),
                imageUri = value.second.image
            )

            when (response) {
                is NetworkResult.Success -> { DataResult.Success(Unit) }
                is NetworkResult.Error -> {
                    val errorMessage = when {
                        response.body?.error?.contains("breedName", ignoreCase = true) == true -> {
                            "A raça selecionada é inválida."
                        }
                        response.body?.error?.contains("too large", ignoreCase = true) == true -> {
                            "A imagem escolhida é muito grande. Por favor, selecione outra menor."
                        }
                        else -> response.body?.error ?: "Ocorreu um erro no servidor."
                    }
                    DataResult.Failure(Throwable(message = errorMessage))
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
