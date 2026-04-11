package com.soujunior.domain.use_case.guardian

import com.soujunior.domain.repository.api.Repository
import com.soujunior.domain.use_case.base.BaseUseCase
import com.soujunior.domain.use_case.base.DataResult

class GetGuardianEmailUseCase(private val repository: Repository) :
    BaseUseCase<Unit, String>() {

    override suspend fun doWork(value: Unit): DataResult<String> {
        return try {
            val email = repository.getGuardianEmail()
            if (email != null) {
                DataResult.Success(email)
            } else {
                DataResult.Failure(Throwable("Email not found in database"))
            }
        } catch (e: Throwable) {
            DataResult.Failure(e)
        }
    }
}
