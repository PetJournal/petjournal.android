package com.soujunior.domain.use_case.auth

import android.content.ContentValues
import android.util.Log
import com.soujunior.domain.model.request.AwaitingCodeModel
import com.soujunior.domain.network.NetworkResult
import com.soujunior.domain.repository.AuthRepository
import com.soujunior.domain.use_case.base.BaseUseCase
import com.soujunior.domain.use_case.base.DataResult
//TODO: Extrair strings para um arquivo strings separado se for possível

class AwaitingCodeUseCase(
    private val repository: AuthRepository
) : BaseUseCase<AwaitingCodeModel, String>() {

    override suspend fun doWork(value: AwaitingCodeModel): DataResult<String> {
        return when (val response = repository.awaitingCode(value)) {
            is NetworkResult.Success -> {
                val success = repository.saveToken(response.data.accessToken)
                if (success) {
                    DataResult.Success("Token Saved")
                } else {
                    DataResult.Failure(Throwable("Error in Save Token!"))
                }
            }

            is NetworkResult.Error -> {
                Log.i(ContentValues.TAG, "${response.code} -> ${response.body?.error}")
                var message = ""
                if (response.code == 401) message = "Codigo expirado ou incorreto!"
                else if (response.code == 500) message = "Erro interno do servidor. Ocorreu um erro inesperado. Tente novamente em alguns instantes."

                DataResult.Failure(Throwable(message = message))
            }
            is NetworkResult.Exception -> DataResult.Failure(response.e)
        }
    }
}