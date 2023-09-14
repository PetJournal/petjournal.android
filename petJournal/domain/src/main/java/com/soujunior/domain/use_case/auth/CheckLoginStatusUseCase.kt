package com.soujunior.domain.use_case.auth

import com.soujunior.domain.repository.AuthRepository
import com.soujunior.domain.use_case.base.BaseUseCase
import com.soujunior.domain.use_case.base.DataResult

class CheckLoginStatusUseCase( private val repository: AuthRepository ) : BaseUseCase<Unit, Boolean>() {
    override suspend fun doWork(value: Unit): DataResult<Boolean> {
        return when (val token = repository.getToken()) {
            null -> DataResult.Failure(Throwable("Error in Get Token!"))
            else -> DataResult.Success(true)
        }
    }
}