package com.soujunior.domain.use_case.auth

import android.util.Log
import com.soujunior.domain.model.request.LoginModel
import com.soujunior.domain.network.NetworkResult
import com.soujunior.domain.repository.api.AuthRepository
import com.soujunior.domain.repository.api.Repository
import com.soujunior.domain.use_case.base.BaseUseCase
import com.soujunior.domain.use_case.base.DataResult

class LoginUseCase(
    private val authRepository: AuthRepository,
    private val repository: Repository
) : BaseUseCase<LoginModel, String>() {
    override suspend fun doWork(value: LoginModel): DataResult<String> {
        Log.d("PJ_LOGIN", "[LoginUseCase] doWork() chamado para email=${value.email}")
        return try {
            val response = authRepository.login(value)
            Log.d("PJ_LOGIN", "[LoginUseCase] authRepository.login() retornou: ${response::class.simpleName}")
            when (response) {
                is NetworkResult.Success -> {
                    Log.d("PJ_LOGIN", "[LoginUseCase] Success — accessToken presente=${response.data.accessToken.isNotEmpty()}")
                    val success = authRepository.saveToken(response.data.accessToken)
                    if (success) {
                        repository.getGuardianName(true)
                        DataResult.Success("Token Saved")
                    } else {
                        Log.e("PJ_LOGIN", "[LoginUseCase] Falha ao salvar token!")
                        DataResult.Failure(Throwable("Error in Save Token!"))
                    }
                }
                is NetworkResult.Error -> {
                    Log.e("PJ_LOGIN", "[LoginUseCase] Error — code=${response.code}, body=${response.body?.error}")
                    DataResult.Failure(Throwable(message = "${response.code} -> ${response.body?.error}"))
                }
                is NetworkResult.Exception -> {
                    Log.e("PJ_LOGIN", "[LoginUseCase] Exception", response.e)
                    DataResult.Failure(response.e)
                }
            }
        } catch (e: Throwable) {
            Log.e("PJ_LOGIN", "[LoginUseCase] CRASH em doWork()", e)
            DataResult.Failure(e)
        }
    }
}
