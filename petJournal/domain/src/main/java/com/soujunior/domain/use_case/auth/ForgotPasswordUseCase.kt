package com.soujunior.domain.use_case.auth

import android.content.ContentValues.TAG
import android.util.Log
import com.soujunior.domain.model.request.ForgotPasswordModel
import com.soujunior.domain.model.response.MessageResponse
import com.soujunior.domain.network.NetworkResult
import com.soujunior.domain.repository.AuthRepository
import com.soujunior.domain.use_case.base.BaseUseCase
import com.soujunior.domain.use_case.base.DataResult

class ForgotPasswordUseCase(
    private val repository: AuthRepository
) : BaseUseCase<ForgotPasswordModel, String>() {

    override suspend fun doWork(value: ForgotPasswordModel): DataResult<String> {
        return repository.forgotPassword(value).toData()
    }

    fun NetworkResult<MessageResponse>.toData(): DataResult<String> {
        return when (this) {
            is NetworkResult.Success -> DataResult.Success(this.data.message)
            is NetworkResult.Error -> {
                Log.i(TAG, "${this.code} -> ${this.body?.error}")
                var message = ""
                if (this.code == 400) message = "Não foi possível encontrar seu email!"
                else if (this.code == 500) message =
                    "Erro do Servidor Interno. Ocorreu um erro inesperado. Por favor, tente novamente em alguns instantes!"
                DataResult.Failure(Throwable(message = message))
            }
            is NetworkResult.Exception -> DataResult.Failure(this.e)
        }
    }
}