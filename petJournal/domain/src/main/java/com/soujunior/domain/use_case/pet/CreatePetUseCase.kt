package com.soujunior.domain.use_case.pet

import android.content.ContentValues.TAG
import android.util.Log
import com.soujunior.domain.model.PetModel
import com.soujunior.domain.model.toDTO
import com.soujunior.domain.network.NetworkResult
import com.soujunior.domain.repository.GuardianRepository
import com.soujunior.domain.use_case.base.BaseUseCase
import com.soujunior.domain.use_case.base.DataResult
import java.io.File

class CreatePetUseCase(private val repository: GuardianRepository) :
    BaseUseCase<PetModel, Unit>() {
    override suspend fun doWork(value: PetModel): DataResult<Unit> {
        return try {
            val response = repository.createPet(
                pet = value.toDTO(),
                imageUri = value.image
            )

            when (response) {
                is NetworkResult.Success -> { DataResult.Success(Unit) }
                is NetworkResult.Error -> {
                    Log.e(TAG, "Error: ${response.code} -> ${response.body?.error}")
                    DataResult.Failure(
                        Throwable(message = "${response.code} -> ${response.body?.error}")
                    )
                }
                is NetworkResult.Exception -> {
                    Log.e(TAG, "Exception: ${response.e}")
                    DataResult.Failure(response.e)
                }
            }
        } catch (e: Throwable) {
            DataResult.Failure(e)
        }
    }
}
